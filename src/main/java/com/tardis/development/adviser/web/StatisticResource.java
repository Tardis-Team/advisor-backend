package com.tardis.development.adviser.web;

import com.tardis.development.adviser.domain.statistic.StatisticService;
import com.tardis.development.adviser.domain.statistic.StatisticView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/users/{username}/statistic")
@RequiredArgsConstructor
public class StatisticResource {

    private final @NonNull StatisticService service;

    @GetMapping("/")
    public Mono<StatisticView> calculate(@PathVariable("username") String username) {
        return service.calculateSaveToSpendAmount(username);
    }

}
