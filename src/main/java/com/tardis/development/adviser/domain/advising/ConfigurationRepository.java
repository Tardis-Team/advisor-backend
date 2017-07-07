package com.tardis.development.adviser.domain.advising;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

interface ConfigurationRepository extends ReactiveMongoRepository<Configuration, String> {

    Mono<Configuration> findByUser(String user);

    @Query("{ 'user' : ?0, 'from' : { '$lte' : ?1 }, 'to' : { '$gte' : ?1 } }")
    Mono<Configuration> findByUserAndFromAfterAndToBefore(String user, LocalDate date);
}
