package com.tardis.development.adviser.domain.statistic;

import com.tardis.development.adviser.domain.transaction.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class DefaultStatisticService implements StatisticService {

    private final @NonNull TransactionService transactions;

    @Override
    public Mono<StatisticView> calculateSaveToSpendAmount(String username) {
        return Mono.fromDirect(Flux.combineLatest(
                Operators::combineUserAdvice,
                1,
                transactions.listAllBeforeThisMonthOrdered(username)
                        .transform(Operators::transformToStatistic)
                        .transform(Operators::combineStatistics),
                transactions.listAllForLastMonth(username)
                        .transform(s -> Operators.transformDirectly(s, LocalDate.now().withDayOfMonth(1).minusMonths(1))),
                transactions.listAllForThisMonth(username)
                        .transform(s -> Operators.transformDirectly(s, LocalDate.now().withDayOfMonth(1)))
        ));
    }
}
