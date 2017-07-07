package com.tardis.development.adviser.domain.contract;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Period;

@Data
@Document
@Immutable
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
class Contract {

    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String id;

    private final @NonNull String creditor;
    private final @NonNull String debitor;
    private final @NonNull Period grace;
    private final @NonNull BigDecimal fine;
    private final @NonNull BigDecimal amount;
}
