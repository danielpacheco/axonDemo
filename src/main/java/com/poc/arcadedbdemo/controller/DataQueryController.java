package com.poc.arcadedbdemo.controller;

import com.arcadedb.graph.Vertex;
import com.poc.arcadedbdemo.repository.DataQueryRepository;
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

	@GetMapping("/query")
	public String query(@RequestParam(value = "type", defaultValue = "Hospital") String type,
							 @RequestParam(value = "query", defaultValue = "") String query) {
		Optional<Vertex> optionalVertex = dataQueryRepository.query(type, query);
		String string = optionalVertex.get().toJSON(true).toString();
		return string;
	}

}

