package com.shaqueiq.arcadedbdemo.cqrs.query;

public class GetHospitalQuery {

    String query = "";

    public GetHospitalQuery(String query) {
        this.query = query;
    }

    //_________________

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
