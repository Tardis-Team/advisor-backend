package com.tardis.development.adviser.domain.advising;

import reactor.core.publisher.Mono;

public interface AdvisingService {

    Mono<UserAdvice> calculateSaveToSpendAmount(String username);

    Mono<Void> saveUserSavingsWishes(String username, Mono<ConfigurationDTO> configuration);

    Mono<ConfigurationDTO> getUserSavingsWishes(String username);

    Mono<Void> updateUserSavingsWishes(String username, Mono<ConfigurationDTO> configuration);
}
