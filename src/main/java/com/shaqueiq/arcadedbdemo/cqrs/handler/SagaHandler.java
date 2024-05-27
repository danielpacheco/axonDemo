package com.shaqueiq.arcadedbdemo.cqrs.handler;

import com.shaqueiq.arcadedbdemo.cqrs.command.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.event.RegistrationEvent;
import com.shaqueiq.arcadedbdemo.model.Constants;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.model.Physician;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
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
    private final CommandGateway commandGateway;

    private Set<Physician> physicians = new HashSet<>();
    private String id= UUID.randomUUID().toString();

    public SagaHandler(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

//    @StartSaga
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
            System.out.println("ok, saga done: "+future.get().toString());
//            System.out.println(STR."ok: \{future.get()}");
            //create edge and affiliate to more several hospital at same time
        }
//        SagaLifecycle.end();
    }

}
