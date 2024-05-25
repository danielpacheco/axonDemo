package com.shaqueiq.arcadedbdemo.cqrs.event;

import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.model.Physician;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public class AffiliateEvent {

    @TargetAggregateIdentifier
    private String id = UUID.randomUUID().toString();
    private Physician physician;
    private Hospital hospital;

    public String getId() {
        return id;
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public AffiliateEvent(String id, Physician physician, Hospital hospital) {
        this.id = id;
        this.physician = physician;
        this.hospital = hospital;
    }
}
