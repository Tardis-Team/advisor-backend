package com.tardis.development.adviser.domain.user;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private final @NonNull String name;
}
