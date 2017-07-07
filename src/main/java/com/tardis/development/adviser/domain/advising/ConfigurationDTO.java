package com.tardis.development.adviser.domain.advising;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigurationDTO {

    private final @NonNull BigDecimal savingsAmount;

    private final @NonNull LocalDate from;

    private final @NonNull LocalDate to;

    @JsonCreator
    public static ConfigurationDTO of(@JsonProperty("savingsAmount") BigDecimal savingsAmount,
                                      @JsonProperty("from") LocalDate from,
                                      @JsonProperty("to") LocalDate to) {
        if (from.isAfter(to) || from.isEqual(to)) {
            throw new IllegalArgumentException("from date MUST be before to date");
        }

        return new ConfigurationDTO(savingsAmount, from, to);
    }
}
