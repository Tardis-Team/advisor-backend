package com.tardis.development.adviser.domain.advising;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Data
@Document
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
class Configuration {

    @Id
    private String id;

    @Indexed(unique = true)
    private final @NonNull String user;

    private final @NonNull BigDecimal savingsAmount;

    private final @NonNull LocalDate from;

    private final @NonNull LocalDate to;

    public BigDecimal getMonthlySavingsAmount() {
        return savingsAmount.divide(BigDecimal.valueOf(Period.between(from, to).getMonths()), BigDecimal.ROUND_CEILING);
    }
}
