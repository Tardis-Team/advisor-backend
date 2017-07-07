package com.tardis.development.adviser.domain.advising;

import com.tardis.development.adviser.domain.transaction.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class DefaultAdvisingService implements AdvisingService {

    private final @NonNull
    TransactionService transactions;
    private final @NonNull
    ConfigurationRepository configurationRepository;

    @Override
    public Mono<UserAdvice> calculateSaveToSpendAmount(String username) {
        return Mono.fromDirect(Flux.combineLatest(
                Operators::combineUserAdvice,
                1,
                transactions.listAllBeforeThisMonthOrdered(username)
                        .transform(Operators::transformToStatistic)
                        .transform(Operators::combineStatistics),
                transactions.listAllForLastMonth(username)
                        .transform(s -> Operators.transformDirectly(s, LocalDate.now().withDayOfMonth(1).minusMonths(1))),
                transactions.listAllForThisMonth(username)
                        .transform(s -> Operators.transformDirectly(s, LocalDate.now().withDayOfMonth(1))),
                configurationRepository.findByUserAndFromAfterAndToBefore(username, LocalDate.now())
                        .map(Optional::of)
                        .defaultIfEmpty(Optional.empty())
        ));
    }

    @Override
    public Mono<Void> saveUserSavingsWishes(String username, Mono<ConfigurationDTO> configuration) {
        return configuration
                .map(c -> Operators.transformToConfiguration(username, c))
                .flatMap(configurationRepository::save)
                .then();
    }

    @Override
    public Mono<ConfigurationDTO> getUserSavingsWishes(String username) {
        return configurationRepository.findByUser(username)
                .map(Operators::transformToConfigurationDTO);
    }

    @Override
    public Mono<Void> updateUserSavingsWishes(String username, Mono<ConfigurationDTO> configuration) {
        return Mono
                .zip(
                        Operators::updateConfiguration,
                        configurationRepository.findByUser(username),
                        configuration
                )
                .flatMap(configurationRepository::save)
                .then();
    }
}
