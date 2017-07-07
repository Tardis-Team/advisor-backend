package com.tardis.development.adviser.domain.transaction;

import com.mongodb.annotations.Immutable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document
@Immutable
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class Transaction {

    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String id;

    private final @NonNull String user;

    private final @NonNull LocalDate date;

    private final @NonNull Type type;

    private final @NonNull BigDecimal amount;

    private final @NonNull Integer categoryCode;
}
