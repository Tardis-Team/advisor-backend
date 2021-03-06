package com.tardis.development.adviser.domain.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value(staticConstructor = "of")
public class TransactionDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final @NonNull LocalDate date;
    private final @NonNull Type type;
    private final @NonNull BigDecimal amount;
    private final @NonNull Integer categoryCode;
}
