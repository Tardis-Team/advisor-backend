package com.tardis.development.adviser.domain.transaction.integration;

import com.tardis.development.adviser.domain.transaction.Transaction;
import reactor.core.publisher.Flux;

import java.io.File;

public interface ExcelTransactionService {

    Flux<Transaction> list(File file);
}
