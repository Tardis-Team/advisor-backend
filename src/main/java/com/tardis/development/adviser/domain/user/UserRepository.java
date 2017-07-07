package com.tardis.development.adviser.domain.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByName(String name);
}
