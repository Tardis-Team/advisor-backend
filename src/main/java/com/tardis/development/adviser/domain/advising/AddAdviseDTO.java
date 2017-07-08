package com.tardis.development.adviser.domain.advising;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Set;

@Value
@RequiredArgsConstructor(staticName = "of")
public class AddAdviseDTO {

    private final @NonNull String category;
    private final @NonNull Type type;
    private final @NonNull String title;
    private final @NonNull String description;
    private final @NonNull String imageLink;
    private final @NonNull Set<Integer> forMCCs;
}
