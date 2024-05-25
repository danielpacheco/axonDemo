package com.shaqueiq.arcadedbdemo.cqrs.handler;

import com.shaqueiq.arcadedbdemo.cqrs.event.AffiliateEvent;
import org.axonframework.modelling.saga.StartSaga;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EventHandler {

    private final Set<String> set = new HashSet<>();

    @org.axonframework.eventhandling.EventHandler
    public void on(AffiliateEvent event) {
        String id = event.getId();
        set.add(id);
    }
}
