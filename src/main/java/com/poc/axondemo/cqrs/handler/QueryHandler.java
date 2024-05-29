package com.poc.axondemo.cqrs.handler;

import com.poc.axondemo.cqrs.query.GetHospitalQuery;
import com.poc.axondemo.cqrs.query.GetPhysicianQuery;
import com.poc.axondemo.model.Hospital;
import com.poc.axondemo.model.Physician;
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
