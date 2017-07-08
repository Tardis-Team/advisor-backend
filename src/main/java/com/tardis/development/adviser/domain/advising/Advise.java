package com.tardis.development.adviser.domain.advising;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
class Advise {

    @Id
    private String id;

    private final @NonNull Set<Integer> forMCCs;
    private final @NonNull String category;
    private final @NonNull String description;
    private final @NonNull String imageLink;
}
