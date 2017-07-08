package com.tardis.development.adviser.web;

import com.tardis.development.adviser.domain.advising.AddAdviseDTO;
import com.tardis.development.adviser.domain.advising.AdviseDTO;
import com.tardis.development.adviser.domain.advising.AdviseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{username}/advises")
public class AdviseResource {

    private final @NonNull AdviseService service;

    @GetMapping
    public Flux<AdviseDTO> list(@PathVariable("username") String username) {

        return service.listAllOrderedByRelevance(username);
    }


    @PostMapping
    public Mono<Void> add(@PathVariable("username") String username,
                               @RequestBody Mono<AddAdviseDTO> body) {

        return service.add(username, body);
    }
}
