package com.tardis.development.adviser.domain.transaction;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
interface CappedTransactionRepository extends ReactiveMongoRepository<CappedTransaction, String> {

    @Tailable
    Flux<CappedTransaction> findAllByUserAndDateAfter(String user, LocalDate date);
}