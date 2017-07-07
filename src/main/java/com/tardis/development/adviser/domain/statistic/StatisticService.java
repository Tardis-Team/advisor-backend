package com.tardis.development.adviser.domain.statistic;

import reactor.core.publisher.Mono;

public interface StatisticService {

    Mono<StatisticView> calculateSaveToSpendAmount(String username);
}
