package com.tardis.development.adviser.domain.user.integration;

import com.tardis.development.adviser.domain.user.User;
import reactor.core.publisher.Flux;

import java.io.File;

public interface ExcelUserService {

    Flux<User> list(File file);
}
