package com.tardis.development.adviser.domain.advising;

import com.tardis.development.adviser.domain.transaction.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
class DefaultAdviseService implements AdviseService {

    private final @NonNull AdviseRepository repository;
    private final @NonNull TransactionService transactions;

    @Override
    public Flux<AdviseDTO> listAllOrderedByRelevance(String username) {
        return repository.findAll()
                .flatMap(ad -> transactions.countAllInCategory(username, ad.getForMCCs())
                        .map(r -> AdviseDTO.of(r, ad.getType(), ad.getCategory(), ad.getDescription(), ad.getImageLink())))
                .sort(Comparator.comparing(AdviseDTO::getRelevance).reversed());
    }

    @Override
    public Mono<Void> add(String username, Mono<AddAdviseDTO> advise) {
        return advise
                .map(aad -> new Advise(aad.getForMCCs(), aad.getType(), aad.getCategory(), aad.getDescription(), aad.getImageLink()))
                .flatMap(repository::save)
                .then();
    }
}
