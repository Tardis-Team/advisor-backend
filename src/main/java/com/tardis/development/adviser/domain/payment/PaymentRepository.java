package com.tardis.development.adviser.domain.payment;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {

    Flux<Payment> findAllByUserAndState(String user, State state);
}
