package com.tardis.development.adviser.domain.advising;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
public class AdviseDTO {

    private final @NonNull Long relevance;
    private final @NonNull Type type;
    private final @NonNull String category;
    private final @NonNull String description;
    private final @NonNull String imageLink;
}
