package com.tardis.development.adviser.web;

import com.tardis.development.adviser.domain.advising.AdvisingService;
import com.tardis.development.adviser.domain.advising.ConfigurationDTO;
import com.tardis.development.adviser.domain.advising.UserAdvice;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/advising")
@RequiredArgsConstructor
public class AdvisingResource {

    private final @NonNull AdvisingService service;

    @GetMapping("/{username}")
    public Mono<UserAdvice> advise(@PathVariable("username") String username) {
        return service.calculateSaveToSpendAmount(username);
    }

    @PostMapping(value = "/{username}/configuration", consumes = MediaType.ALL_VALUE)
    public Mono<Void> addConfiguration(@PathVariable("username") String username,
                                @RequestBody Mono<ConfigurationDTO> configuration) {
        return service.saveUserSavingsWishes(username, configuration);
    }

    @PutMapping(value = "/{username}/configuration", consumes = MediaType.ALL_VALUE)
    public Mono<Void> updateConfiguration(@PathVariable("username") String username,
                                @RequestBody Mono<ConfigurationDTO> configuration) {
        return service.updateUserSavingsWishes(username, configuration);
    }

    @GetMapping(value = "/{username}/configuration")
    public Mono<ConfigurationDTO> getConfiguration(@PathVariable("username") String username) {
        return service.getUserSavingsWishes(username);
    }
}
