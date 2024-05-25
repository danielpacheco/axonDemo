package com.shaqueiq.arcadedbdemo.service;

import com.shaqueiq.arcadedbdemo.cqrs.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import org.axonframework.messaging.ExecutionException;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HospitalQueryService {

//    @QueryHandler
//    public Hospital getHospital(GetHospitalQuery query) throws ExecutionException {
//        //Invoke ArcadeDB
//        return new Hospital(query.getQuery());
//    }

    @QueryHandler(queryName = "getHospital")
    public List<Hospital> getHospital(GetHospitalQuery query) throws ExecutionException {
//        return new Hospital(query.getQuery());
        return Collections.singletonList(new Hospital(query.getQuery()));
    }


}
