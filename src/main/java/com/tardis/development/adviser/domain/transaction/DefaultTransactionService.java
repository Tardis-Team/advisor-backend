package com.tardis.development.adviser.domain.transaction;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class DefaultTransactionService implements TransactionService {

    private final @NonNull TransactionRepository repository;

    @Override
    public Flux<TransactionDTO> listAllBeforeThisMonthOrdered(String username) {
        return repository
                .findAllByUserAndDateBetweenOrderByDateDesc(
                        username,
                        LocalDate.now().minusMonths(6).withDayOfMonth(1),
                        LocalDate.now().minusMonths(6).withDayOfMonth(1)
                )
                .map(Operators::transformToDTO);
    }

    @Override
    public Flux<TransactionDTO> listAllForThisMonth(String username) {
        return repository
                .findAllByUserAndDateBetween(
                        username,
                        LocalDate.now().withDayOfMonth(1).minusDays(1),
                        LocalDate.now().plusMonths(1).withDayOfMonth(1)
                )
                .map(Operators::transformToDTO);
    }

    @Override
    public Flux<TransactionDTO> listAllForLastMonth(String username) {
        return repository
                .findAllByUserAndDateBetween(
                        username,
                        LocalDate.now().minusMonths(1).withDayOfMonth(1).minusDays(1),
                        LocalDate.now().withDayOfMonth(1)
                )
                .map(Operators::transformToDTO);
    }

    @Override
    public Flux<TransactionDTO> listAll(String username) {
        return repository
                .findAllByUser(username)
                .map(Operators::transformToDTO);
    }

    @Override
    public Mono<Void> add(String username, Mono<TransactionDTO> dto) {
        return dto
                .map(value -> Operators.transformToDomain(username, value))
                .flatMap(repository::save)
                .then();
    }
}
