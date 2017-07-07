package com.tardis.development.adviser;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.connection.*;
import com.mongodb.connection.netty.NettyStreamFactory;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@SpringBootApplication
public class AdviserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdviserApplication.class, args);
    }

    @Configuration
    @RequiredArgsConstructor
    @Profile("heroku")
    static class MongoConfig {
        private final Environment environment;


        @Bean
        public MongoClient mongoClient() {
            String uri = environment.getProperty("spring.data.mongodb.uri", String.class);
            ConnectionString connectionString = new ConnectionString(uri);
            MongoClientSettings.Builder builder = MongoClientSettings.builder()
                    .streamFactoryFactory(NettyStreamFactory::new)
                    .clusterSettings(ClusterSettings.builder()
                            .applyConnectionString(connectionString)
                            .build())
                    .connectionPoolSettings(ConnectionPoolSettings.builder()
                            .applyConnectionString(connectionString)
                            .build())
                    .serverSettings(ServerSettings.builder()
                            .applyConnectionString(connectionString)
                            .build())
                    .credentialList(connectionString.getCredentialList())
                    .sslSettings(SslSettings.builder()
                            .applyConnectionString(connectionString)
                            .build())
                    .socketSettings(SocketSettings.builder()
                            .applyConnectionString(connectionString)
                            .build());

            if (connectionString.getReadPreference() != null) {
                builder.readPreference(connectionString.getReadPreference());
            }
            if (connectionString.getReadConcern() != null) {
                builder.readConcern(connectionString.getReadConcern());
            }
            if (connectionString.getWriteConcern() != null) {
                builder.writeConcern(connectionString.getWriteConcern());
            }
            if (connectionString.getApplicationName() != null) {
                builder.applicationName(connectionString.getApplicationName());
            }
            return MongoClients.create(builder.build());
        }
    }
}
