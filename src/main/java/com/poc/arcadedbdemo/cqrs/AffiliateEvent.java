package com.poc.arcadedbdemo.cqrs;

import com.poc.arcadedbdemo.model.Physician;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AffiliateEvent {

    @TargetAggregateIdentifier
    private final String id = "";

    public String getId() {
        return id;
    }

    public AffiliateEvent(String id, Physician physician) {
    }

}
