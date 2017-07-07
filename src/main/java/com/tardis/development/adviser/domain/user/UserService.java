package com.tardis.development.adviser.domain.user;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> get(String name);
}
