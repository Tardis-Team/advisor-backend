package com.tardis.development.adviser.domain.advising;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdviseService {

    Flux<AdviseDTO> listAllOrderedByRelevance(String username);

    Mono<Void> add(String username, Mono<AddAdviseDTO> advise);
}
