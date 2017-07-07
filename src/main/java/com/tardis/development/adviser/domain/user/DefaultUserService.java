package com.tardis.development.adviser.domain.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class DefaultUserService implements UserService {

    private final @NonNull UserRepository repository;

    @Override
    public Mono<User> get(String name) {
        return repository.findByName(name);
    }
}
