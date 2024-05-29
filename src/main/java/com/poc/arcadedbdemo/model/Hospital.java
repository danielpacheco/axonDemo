package com.poc.arcadedbdemo.model;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

import java.util.UUID;

public class Hospital implements Constants {

    @AggregateIdentifier
    private String id;
    private String name;

    //    _______________________

    public Hospital() {}

    public Hospital(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hospital(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    //    _______________________

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    _______________________


}
