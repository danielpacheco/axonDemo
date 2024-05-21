package com.shaqueiq.arcadedbdemo.controller;

import com.shaqueiq.arcadedbdemo.cqrs.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.service.HospitalAgreggateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/", produces="application/json")
public class DataQueryController {

	@Autowired
	private HospitalAgreggateService hospitalAgreggateService;

	@GetMapping("/queryCommand")
	public Hospital queryCommand(@RequestParam(value = "query", defaultValue = "") String query) {
		Hospital hospital = hospitalAgreggateService.getHospital(new GetHospitalQuery(query));
		return hospital;
	}

}

