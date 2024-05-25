package com.shaqueiq.arcadedbdemo.cqrs.handler;

import com.shaqueiq.arcadedbdemo.cqrs.query.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.cqrs.query.GetPhysicianQuery;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.model.Physician;
import org.axonframework.messaging.ExecutionException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QueryHandler {

    @org.axonframework.queryhandling.QueryHandler(queryName = "getHospital")
    public List<Hospital> getHospital(GetHospitalQuery query) throws ExecutionException {
        return Collections.singletonList(new Hospital(query.getQuery()));
    }

    @org.axonframework.queryhandling.QueryHandler(queryName = "getPhysician")
    public Physician getPhysician(GetPhysicianQuery query) throws ExecutionException {
        return new Physician(query.getName());
    }

}
