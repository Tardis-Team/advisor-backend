package com.tardis.development.adviser.integration;

import com.tardis.development.adviser.domain.transaction.ExcelTransactionDTO;
import reactor.core.publisher.Flux;

import java.io.File;

public interface ExcelTransactionService {

    Flux<ExcelTransactionDTO> list(File file);
}
