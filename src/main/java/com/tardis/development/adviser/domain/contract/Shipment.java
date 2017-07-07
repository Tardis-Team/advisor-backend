package com.tardis.development.adviser.domain.contract;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
class Shipment {

    @Id
    private String id;

    private final Contract contract;


}
