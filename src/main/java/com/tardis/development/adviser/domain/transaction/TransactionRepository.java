package com.tardis.development.adviser.domain.transaction;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Set;

@Repository
interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {


    Mono<Long> countAllByUserAndCategoryCodeIn(String user, Set<Integer> codes);

    Flux<Transaction> findAllByUserAndDateBetweenOrderByDateDesc(String user, LocalDate start, LocalDate end);

    Flux<Transaction> findAllByUserAndDateBetween(String user, LocalDate start, LocalDate end);

    Flux<Transaction> findAllByUserAndDateBetweenAndAndType(String user, LocalDate start, LocalDate end, Type type);
}
