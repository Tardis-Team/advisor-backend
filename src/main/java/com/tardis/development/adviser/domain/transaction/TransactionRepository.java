package com.tardis.development.adviser.domain.transaction;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    @Tailable
    Flux<Transaction> findAllByUserAndDateAfter(String user, LocalDate date);

    Flux<Transaction> findAllByUserAndDateBetweenOrderByDateDesc(String user, LocalDate start, LocalDate end);

    Flux<Transaction> findAllByUserAndDateBetween(String user, LocalDate start, LocalDate end);
}
