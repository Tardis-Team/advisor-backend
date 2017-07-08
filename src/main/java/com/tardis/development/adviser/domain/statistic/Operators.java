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
                        PeriodStatistic.Builder::new,
                        (b, i) -> b.add(i.remainder(), i.spending()),
                        PeriodStatistic.Builder::add
                ))
                .map(PeriodStatistic.Builder::build);
    }

    @SuppressWarnings("unchecked")
    static StatisticView combineUserAdvice(Object[] args) {
        PeriodStatistic periodStatistic = (PeriodStatistic) args[0];
        BigDecimal reminderDeviation = periodStatistic.getReminderDeviation();
        BigDecimal totalAverageReminder = periodStatistic.getAverageReminder();
        BigDecimal totalAverageSpending = periodStatistic.getAverageSpending();
        BigDecimal lastReminder = (BigDecimal) args[1];
        BigDecimal plannedReminder = ((BigDecimal) args[2])
                .subtract(periodStatistic.getAverageSpending());
        BigDecimal saveToSpend = plannedReminder.compareTo(totalAverageReminder) > 0
                ? plannedReminder.subtract(reminderDeviation)
                : plannedReminder;

        return StatisticView.of(
                totalAverageReminder,
                totalAverageSpending,
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
