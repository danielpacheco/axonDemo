package com.poc.arcadedbdemo.controller;

import com.poc.arcadedbdemo.service.GraphDBCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.poc.arcadedbdemo.model.Result;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DataBaseCreationController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private GraphDBCreatorService graphDbCreatorService;
	
	@GetMapping("/create")
	public Result create(@RequestParam(value = "name", defaultValue = "Good") String msg) {
		graphDbCreatorService.create();
		return new Result(msg);
	}
}

