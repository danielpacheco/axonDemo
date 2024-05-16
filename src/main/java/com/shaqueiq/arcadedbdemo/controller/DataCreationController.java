package com.shaqueiq.arcadedbdemo.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.shaqueiq.arcadedbdemo.service.GraphDBCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.shaqueiq.arcadedbdemo.model.Result;

@RestController
public class DataCreationController {
	
	@Autowired
	private GraphDBCreatorService graphDbCreatorService;
	
	@GetMapping("/create")
	public Result create(@RequestParam(value = "name", defaultValue = "Good") String msg) {
		graphDbCreatorService.create();
		return new Result(msg);
	}
}

