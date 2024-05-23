package com.shaqueiq.arcadedbdemo.controller;

import com.shaqueiq.arcadedbdemo.cqrs.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.cqrs.aggregate.HospitalAggregate;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.model.Physician;
import com.shaqueiq.arcadedbdemo.service.HospitalAgreggateQueryGateway;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path="/", produces="application/json")
public class CommandController {

	@Autowired
	private HospitalAgreggateQueryGateway hospitalAgreggateQueryGateway;

	@Autowired
	private HospitalAggregate hospitalAggregate;

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping("/queryCommand")
	public Hospital queryCommand(@RequestParam(value = "query", defaultValue = "") String query) {
		Hospital hospital = hospitalAgreggateQueryGateway.getHospital(new GetHospitalQuery(query));
		return hospital;
	}

	@PostMapping("/affiliateCommand")
	public ResponseEntity<String> affiliateCommand(
			@RequestParam(value = "name", defaultValue = "Jose Aleman") String name) throws ExecutionException,
			InterruptedException {

		CompletableFuture<Object> future = commandGateway.send(
				new AffiliateCommand(UUID.randomUUID().toString(), new Physician(name), new Hospital("Mayo Clinics")));
		Object o = future.get();
		return ResponseEntity.ok("ok: "+o.toString());
	}

}

