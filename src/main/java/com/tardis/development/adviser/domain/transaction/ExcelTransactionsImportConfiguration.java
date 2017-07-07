package com.tardis.development.adviser.domain.transaction;

import com.tardis.development.adviser.domain.transaction.integration.ExcelTransactionService;
import com.tardis.development.adviser.domain.user.ExcelUsersImportConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;

@Configuration
@AutoConfigureAfter(ExcelUsersImportConfiguration.class)
public class ExcelTransactionsImportConfiguration {

    @Bean
    @Order
    public CommandLineRunner transactionsCommandLineRunner(ApplicationContext context,
                                                           TransactionRepository repository,
                                                           ExcelTransactionService importService) {
        return args -> {
            if (args.length > 0) {
                Resource resource = context.getResource(args[0]);

                if (resource.exists()) {
                    importService.list(resource.getFile())
                            .map(t -> repository.save(t).block())
                            .subscribe(v -> { }, e -> { });
                }
            }
        };
    }
}
