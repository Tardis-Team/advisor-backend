package com.tardis.development.adviser.domain.statistic;

import com.tardis.development.adviser.domain.transaction.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

final class Operators {

    private Operators() {
    }

    static Mono<PeriodStatistic> combineStatistics(Flux<MonthlyStatistic> source) {
        return source
                .collect(Collector.of(
                        () -> new Object[]{0, BigDecimal.ZERO, BigDecimal.ZERO},
                        (ll, i) -> {
                            int l = (int) ll[0];
                            ll[0] = l + 1;
                            ll[1] = ((BigDecimal) ll[1]).add(i.remainder());
                            ll[2] = ((BigDecimal) ll[2]).add(i.spending());
                        },
                        (ll, rr) -> {
                            int left = (int) ll[0];
                            ll[0] = left + (int) rr[0];
                            ll[1] = ((BigDecimal) ll[1]).add((BigDecimal) rr[1]);
                            ll[2] = ((BigDecimal) ll[2]).add((BigDecimal) rr[2]);
                            return ll;
                        }
                ))
                .map(args -> PeriodStatistic.of((int) args[0], (BigDecimal) args[1], (BigDecimal) args[2]));
    }

    @SuppressWarnings("unchecked")
    static StatisticView combineUserAdvice(Object[] args) {
        PeriodStatistic periodStatistic = (PeriodStatistic) args[0];
        BigDecimal lastReminder = (BigDecimal) args[1];
        BigDecimal plannedReminder = ((BigDecimal) args[2]).subtract(periodStatistic.getTotalAverageSpending());
        BigDecimal saveToSpend = BigDecimal.ZERO;

        return StatisticView.of(
                periodStatistic.getTotalAverageReminder(),
                lastReminder,
                plannedReminder,
                saveToSpend
        );
    }

    static Mono<BigDecimal> transformDirectly(Flux<TransactionDTO> source, LocalDate initial) {
        return source
                .reduce(MonthlyStatistic.of(initial), MonthlyStatistic::put)
                .map(MonthlyStatistic::remainder);
    }

    static Flux<MonthlyStatistic> transformToStatistic(Flux<TransactionDTO> source) {
        return source
                .scan(
                        MonthlyStatistic.of(LocalDate.now().withDayOfMonth(1).minusMonths(1)),
                        (init, inc) -> init.isInCurrentPeriod(inc) ? init.put(inc) : MonthlyStatistic.of(inc)
                )
                .collect(Collectors.toSet())
                .flatMapIterable(Function.identity());
    }
}
