package com.shaqueiq.arcadedbdemo.config;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.queryhandling.SimpleQueryBus;
import org.axonframework.tracing.SpanFactory;
import org.axonframework.metrics.GlobalMetricRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AxonConfig {

    public Configurer jpaEventStorageConfigurer(EntityManagerProvider entityManagerProvider,
                                                TransactionManager transactionManager) {
        return DefaultConfigurer.jpaConfiguration(entityManagerProvider, transactionManager);
    }

    // The InMemoryEventStorageEngine stores each event in memory.
    @Bean
    public EventStorageEngine storageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Bean
    public QueryBus queryBus(GlobalMetricRegistry metricRegistry,
                             SpanFactory spanFactory,
                             TransactionManager transactionManager,
                             QueryUpdateEmitter updateEmitter) {
        return SimpleQueryBus.builder()
                .messageMonitor(metricRegistry.registerQueryBus("queryBus"))
                .transactionManager(transactionManager)
                .spanFactory(spanFactory)
                .queryUpdateEmitter(updateEmitter)
                .build();
    }
}
