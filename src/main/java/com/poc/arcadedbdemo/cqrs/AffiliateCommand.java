package com.poc.arcadedbdemo.cqrs;

import com.poc.arcadedbdemo.model.Physician;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AffiliateCommand {

    @TargetAggregateIdentifier
    private final String id = "";

    public String getId() {
        return id;
    }

    public Physician getPhysician() {
        return null;
    }
}
