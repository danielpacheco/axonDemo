package com.poc.axondemo.cqrs.handler;

import com.poc.axondemo.cqrs.event.AffiliateEvent;
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
