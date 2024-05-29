package com.poc.arcadedbdemo.controller;

import com.arcadedb.graph.Vertex;
import com.poc.arcadedbdemo.cqrs.GetHospitalQuery;
import com.poc.arcadedbdemo.model.Hospital;
import com.poc.arcadedbdemo.repository.DataQueryRepository;
import com.poc.arcadedbdemo.service.HospitalAgreggateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path="/", produces="application/json")
public class DataQueryController {

	@Autowired
	private DataQueryRepository dataQueryRepository;

	@Autowired
	private HospitalAgreggateService hospitalAgreggateService;

	@GetMapping("/query")
	public String query(@RequestParam(value = "type", defaultValue = "Hospital") String type,
							 @RequestParam(value = "query", defaultValue = "") String query) {
		Optional<Vertex> optionalVertex = dataQueryRepository.query(type, query);
		String string = optionalVertex.get().toJSON(true).toString();
		return string;
	}

	@GetMapping("/queryCommand")
	public Hospital queryCommand(@RequestParam(value = "query", defaultValue = "") String query) {
		Hospital hospital = hospitalAgreggateService.getHospital(new GetHospitalQuery(query));
		return hospital;
	}

}

