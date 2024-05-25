package com.shaqueiq.arcadedbdemo.controller;

import com.shaqueiq.arcadedbdemo.cqrs.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.cqrs.aggregate.HospitalAggregate;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.model.Physician;
import com.shaqueiq.arcadedbdemo.service.HospitalQueryService;
//import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path="/", produces="application/json")
public class CommandController {

//	@Autowired
//	private HospitalQueryService hospitalQueryService;

	@Autowired
	private HospitalAggregate hospitalAggregate;

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping("/queryCommand")
	public Hospital queryCommand(@RequestParam(value = "query", defaultValue = "") String query)
			throws ExecutionException, InterruptedException {

		CompletableFuture<List<Hospital>> future =
				queryGateway.query("getHospital", new GetHospitalQuery(query), ResponseTypes.multipleInstancesOf(Hospital.class));
//				queryGateway.query("getHospital", new GetHospitalQuery(query), ResponseTypes.multipleInstancesOf(Hospital.class));
		return future.get().get(0);
	}

	@PostMapping("/affiliateCommand")
	public ResponseEntity<String> affiliateCommand(
			@RequestParam(value = "name", defaultValue = "Jose Aleman") String name)
			throws ExecutionException, InterruptedException {

		CompletableFuture<String> future = commandGateway.send(
				new AffiliateCommand(UUID.randomUUID().toString(), new Physician(name), new Hospital("Mayo Clinics")));
		return ResponseEntity.ok("ok: "+future.get());
	}

}

