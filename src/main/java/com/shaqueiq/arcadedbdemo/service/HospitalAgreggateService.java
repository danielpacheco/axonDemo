package com.shaqueiq.arcadedbdemo.service;

import com.arcadedb.graph.Vertex;
import com.shaqueiq.arcadedbdemo.cqrs.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.model.Constants;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.repository.DataQueryRepository;
import org.axonframework.messaging.ExecutionException;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HospitalAgreggateService {

    @Autowired
    DataQueryRepository dataQueryRepository;

    @Autowired
    DataQueryRepository hospitalRepository;

    @QueryHandler
    public Hospital getHospital(GetHospitalQuery query) throws ExecutionException {

        Optional<Vertex> optionalVertex = dataQueryRepository.query(Constants.HOSPITAL, query.getQuery());
        AtomicReference<Hospital> result = new AtomicReference<>();
        optionalVertex.ifPresent( vertex -> {
            result.set(Hospital.fromVertex(vertex));
        });
        return result.get();
    }

}
