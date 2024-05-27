package com.shaqueiq.arcadedbdemo.cqrs.aggregate;

import com.shaqueiq.arcadedbdemo.cqrs.command.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.command.RegistrationCommand;
import com.shaqueiq.arcadedbdemo.cqrs.event.AffiliateEvent;
import com.shaqueiq.arcadedbdemo.cqrs.event.RegistrationEvent;
import com.shaqueiq.arcadedbdemo.model.Constants;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.model.Physician;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class HospitalAggregate {

    @AggregateIdentifier
    private String id = UUID.randomUUID().toString();
    private Set<Physician> physicians = new HashSet<>();

    @Autowired
    private CommandGateway commandGateway;

    protected HospitalAggregate() {}


    @CommandHandler
    public HospitalAggregate(AffiliateCommand command) {
        apply(new AffiliateEvent(command.getId(), command.getPhysician(), command.getHospital()));
    }

    @CommandHandler
    public HospitalAggregate(RegistrationCommand command) {
        apply(new RegistrationEvent(command.getPhysician(), command.getHospital()));
    }

    @SagaEventHandler(associationProperty = "id")
    public void on(RegistrationEvent event) throws ExecutionException, InterruptedException {
        id = event.getId();
        registrationSaga(event);
    }

    public void registrationSaga(RegistrationEvent event) throws ExecutionException, InterruptedException {

        String name = event.getPhysician().Name();
        //not member of X association
        if (!name.equals(Constants.DANIEL_P)) {
            physicians.add(event.getPhysician());
//            CompletableFuture<String> future = commandGateway.send(
//                    new AffiliateCommand(UUID.randomUUID().toString(), new Physician(name), new Hospital(Constants.MAYO_CLINICS)));
            System.out.println("ok saga, physician added: "+name);
//            System.out.println(STR."ok: \{future.get()}");
            //create edge and affiliate to more several hospital at same time
        }
        System.out.println("ok, saga finished");
//        SagaLifecycle.end();
    }

    @EventSourcingHandler
    public void on(AffiliateEvent event) {
        id = event.getId();
    }

}
