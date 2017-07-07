package com.tardis.development.adviser.domain.payment;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
class Payment {

    @Id
    private String id;
    private final @NonNull String user;
    private final @NonNull String description;
    private final @NonNull BigDecimal amount;
    private final @NonNull Period frequency;
    private final @NonNull LocalDate dueDate;
    private final @NonNull State state;
}
