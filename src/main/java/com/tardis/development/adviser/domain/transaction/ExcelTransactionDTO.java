package com.tardis.development.adviser.domain.transaction;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value(staticConstructor = "of")
public class ExcelTransactionDTO {

    private final @NonNull String user;

    private final @NonNull LocalDate date;

    private final @NonNull Type type;

    private final @NonNull BigDecimal amount;

    private final @NonNull Integer categoryCode;
}
