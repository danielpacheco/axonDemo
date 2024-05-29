package com.poc.axondemo.cqrs.event;

import com.poc.axondemo.model.Hospital;
import com.poc.axondemo.model.Physician;

import java.util.UUID;

public class RegistrationEvent {

//    @TargetAggregateIdentifier
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

    public RegistrationEvent(Physician physician, Hospital hospital) {
        this.physician = physician;
        this.hospital = hospital;
    }
}
