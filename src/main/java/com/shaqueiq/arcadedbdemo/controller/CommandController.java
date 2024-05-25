package com.shaqueiq.arcadedbdemo.controller;

import com.shaqueiq.arcadedbdemo.cqrs.command.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.command.RegistrationCommand;
import com.shaqueiq.arcadedbdemo.cqrs.query.GetHospitalQuery;
import com.shaqueiq.arcadedbdemo.cqrs.aggregate.HospitalAggregate;
import com.shaqueiq.arcadedbdemo.model.Constants;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.model.Physician;
//import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaLifecycle;
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

	@Autowired
	private HospitalAggregate hospitalAggregate;

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping("/queryCommand")
	public Hospital queryCommand(@RequestParam(value = Constants.QUERY, defaultValue = "") String query)
			throws ExecutionException, InterruptedException {

		CompletableFuture<List<Hospital>> future =
				queryGateway.query(Constants.GET_HOSPITAL, new GetHospitalQuery(query), ResponseTypes.multipleInstancesOf(Hospital.class));
//				queryGateway.query("getHospital", new GetHospitalQuery(query), ResponseTypes.multipleInstancesOf(Hospital.class));
		return future.get().get(0);
	}

	@PostMapping("/affiliateCommand")
	public ResponseEntity<String> affiliateCommand(
			@RequestParam(value = Constants.NAME, defaultValue = Constants.JOSE_ALEMAN) String name)
			throws ExecutionException, InterruptedException {

		CompletableFuture<String> future = commandGateway.send(
				new AffiliateCommand(UUID.randomUUID().toString(), new Physician(name), new Hospital(Constants.MAYO_CLINICS)));
		return ResponseEntity.ok("ok: "+future.get());
//		return ResponseEntity.ok(STR."ok: \{future.get()}");
	}

	@PostMapping("/registrationSaga")
	public ResponseEntity<String> registrationSaga(
			@RequestParam(value = Constants.NAME, defaultValue = Constants.DANIEL_P) String name)
			throws ExecutionException, InterruptedException {

//		SagaLifecycle.associateWith(Constants.ID, UUID.randomUUID().toString());
		CompletableFuture<String> future = commandGateway.send(
				new RegistrationCommand("111", new Physician(name), new Hospital(Constants.MAYO_CLINICS)));
//		return ResponseEntity.ok(STR."ok: \{future.get()}");
		return ResponseEntity.ok("ok: "+future.get());
	}

}

