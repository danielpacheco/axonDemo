package com.poc.axondemo.cqrs.handler;

import com.poc.axondemo.cqrs.command.AffiliateCommand;
import com.poc.axondemo.cqrs.event.RegistrationEvent;
import com.poc.axondemo.model.Constants;
import com.poc.axondemo.model.Hospital;
import com.poc.axondemo.model.Physician;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Saga(sagaStore = "mySagaStore")
public class SagaHandler {

    @Autowired
    private CommandGateway commandGateway;

    private Set<Physician> physicians = new HashSet<>();
    private String id= UUID.randomUUID().toString();

    public SagaHandler() {
    }

    @StartSaga
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
            CompletableFuture<String> future = commandGateway.send(
                    new AffiliateCommand(UUID.randomUUID().toString(), new Physician(name), new Hospital(Constants.MAYO_CLINICS)));
            System.out.println("new physician added: "+name);
        }
        System.out.println("ok, saga done");
    }

}
