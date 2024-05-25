package com.shaqueiq.arcadedbdemo.cqrs.aggregate;

import com.shaqueiq.arcadedbdemo.cqrs.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.AffiliateEvent;
import com.shaqueiq.arcadedbdemo.cqrs.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.ExecutionException;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class HospitalAggregate {

    @AggregateIdentifier
    private String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

    protected HospitalAggregate() {
    }

    @CommandHandler
    public HospitalAggregate(AffiliateCommand command) {
        apply(new AffiliateEvent(command.getId(), command.getPhysician(), command.getHospital()));
    }

    @EventSourcingHandler
    public void on(AffiliateEvent event) {
        id = event.getId();
    }

}
