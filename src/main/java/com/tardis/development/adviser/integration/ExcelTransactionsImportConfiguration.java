package com.tardis.development.adviser.integration;

import com.tardis.development.adviser.domain.transaction.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;

@Configuration
public class ExcelTransactionsImportConfiguration {

    @Bean
    @Order
    public CommandLineRunner transactionsCommandLineRunner(ApplicationContext context,
                                                           TransactionService service,
                                                           ExcelTransactionService importService) {
        return args -> {
            if (args.length > 0) {
                Resource resource = context.getResource(args[0]);

                if (resource.exists()) {
                    importService.list(resource.getFile())
                            .map(t -> service.add(t).block())
                            .subscribe(v -> { }, e -> { });
                }
            }
        };
    }
}
