package com.tardis.development.adviser.domain.transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface TransactionService {
    Mono<Long> countAllInCategory(String username, Set<Integer> codes);

    Flux<TransactionDTO> listAllBeforeThisMonthOrdered(String username);

    Flux<TransactionDTO> listAllForThisMonth(String username);

    Flux<TransactionDTO> listAllForLastMonth(String username);

    Flux<TransactionDTO> listAllUpcoming(String username);

    Flux<TransactionDTO> listAll(String username);

    Mono<Void> add(String username, Mono<TransactionDTO> dto);

    Mono<Void> add(ExcelTransactionDTO dto);
}
