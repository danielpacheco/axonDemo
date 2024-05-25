package com.shaqueiq.arcadedbdemo.cqrs.aggregate;

import com.shaqueiq.arcadedbdemo.cqrs.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.AffiliateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class HospitalAggregate {

    @AggregateIdentifier
    private String id = UUID.randomUUID().toString();

    protected HospitalAggregate() {}

    @CommandHandler
    public HospitalAggregate(AffiliateCommand command) {
        apply(new AffiliateEvent(command.getId(), command.getPhysician(), command.getHospital()));
    }

    @EventSourcingHandler
    public void on(AffiliateEvent event) {
        id = event.getId();
    }

//    @SagaEventHandler(associationProperty = "")
//    public void registrationSaga() {
//
//    }

}
