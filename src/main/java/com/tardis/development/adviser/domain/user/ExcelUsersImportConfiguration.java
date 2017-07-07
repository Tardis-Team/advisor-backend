package com.tardis.development.adviser.domain.user;

import com.tardis.development.adviser.domain.transaction.ExcelTransactionsImportConfiguration;
import com.tardis.development.adviser.domain.user.integration.ExcelUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore(ExcelTransactionsImportConfiguration.class)
public class ExcelUsersImportConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CommandLineRunner usersCommandLineRunner(
            ApplicationContext context,
            UserRepository repository,
            ExcelUserService excelUserService) {

        return args -> {
            if (args.length > 0) {
                Resource resource = context.getResource(args[0]);

                if (resource.exists()) {
                    excelUserService
                            .list(resource.getFile())
                            .transform(repository::saveAll)
                            .subscribe();
                }
            }
        };
    }
}
