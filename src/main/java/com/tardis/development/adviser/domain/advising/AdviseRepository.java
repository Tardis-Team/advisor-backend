package com.tardis.development.adviser.domain.advising;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface AdviseRepository extends ReactiveMongoRepository<Advise, String> {
}
