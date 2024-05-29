package com.poc.axondemo.cqrs.query;

public class GetPhysicianQuery {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GetPhysicianQuery(String name) {
        this.name = name;
    }
}
