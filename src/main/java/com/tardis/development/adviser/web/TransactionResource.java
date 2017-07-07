package com.tardis.development.adviser.web;

import com.tardis.development.adviser.domain.transaction.TransactionDTO;
import com.tardis.development.adviser.domain.transaction.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users/{username}/transactions")
@RequiredArgsConstructor
public class TransactionResource {

    private final @NonNull TransactionService service;

    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TransactionDTO> list(@PathVariable("username") String username) {

        return service.listAll(username);
    }

    @PostMapping("/")
    public Mono<Void> add(@PathVariable("username") String username,
                          @RequestBody Mono<TransactionDTO> body) {

        return service.add(username, body);
    }
}
