package com.tardis.development.adviser.domain.contract;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ContractRepository extends ReactiveMongoRepository<Contract, String> {
}
