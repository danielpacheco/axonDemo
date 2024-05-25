package com.shaqueiq.arcadedbdemo.cqrs.handler;

import com.shaqueiq.arcadedbdemo.cqrs.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import org.axonframework.messaging.ExecutionException;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HospitalQueryHandler {

    @QueryHandler(queryName = "getHospital")
    public List<Hospital> getHospital(GetHospitalQuery query) throws ExecutionException {
        return Collections.singletonList(new Hospital(query.getQuery()));
    }


}
