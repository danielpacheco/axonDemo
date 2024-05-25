package com.shaqueiq.arcadedbdemo.cqrs.handler;

import com.shaqueiq.arcadedbdemo.cqrs.AffiliateEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class HospitalEventHandler {

    private final Set<String> set = new HashSet<>();

    @EventHandler
    public void on(AffiliateEvent event) {
        String id = event.getId();
        set.add(id);
    }
}
