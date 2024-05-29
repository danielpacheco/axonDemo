package com.poc.axondemo.cqrs.command;

import com.poc.axondemo.model.Hospital;
import com.poc.axondemo.model.Physician;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public class RegistrationCommand {

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

    public Hospital getHospital() {
        return hospital;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public RegistrationCommand(String id, Physician physician, Hospital hospital) {
        this.id = id;
        this.physician = physician;
        this.hospital = hospital;
    }
}
