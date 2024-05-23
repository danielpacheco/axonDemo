package com.shaqueiq.arcadedbdemo.service;

import com.shaqueiq.arcadedbdemo.cqrs.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import org.axonframework.messaging.ExecutionException;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class HospitalAgreggateQueryGateway {

    @QueryHandler
    public Hospital getHospital(GetHospitalQuery query) throws ExecutionException {
        //Invoke ArcadeDB
        return new Hospital(query.getQuery());
    }

}
