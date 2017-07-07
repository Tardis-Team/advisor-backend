package com.tardis.development.adviser.domain.transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<TransactionDTO> listAllBeforeThisMonthOrdered(String username);

    Flux<TransactionDTO> listAllForThisMonth(String username);

    Flux<TransactionDTO> listAllForLastMonth(String username);

    Flux<TransactionDTO> listAll(String username);

    Mono<Void> add(String username, Mono<TransactionDTO> dto);
}
