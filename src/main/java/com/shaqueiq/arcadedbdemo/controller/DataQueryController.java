package com.shaqueiq.arcadedbdemo.controller;

import com.arcadedb.graph.Vertex;
import com.arcadedb.serializer.json.JSONObject;
import com.shaqueiq.arcadedbdemo.service.DataQueryService;
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
	private DataQueryService dataQueryService;

	@GetMapping("/testQuery")
	public String create(@RequestParam(value = "type", defaultValue = "Hospital") String type,
							 @RequestParam(value = "query", defaultValue = "") String query) {
		Optional<Vertex> optionalVertex = dataQueryService.query(type, query);
//		HttpHeaders headers = new HttpHeaders();
//		ResponseEntity<Record> entity = new ResponseEntity<>(optionalRecord.get(), headers, HttpStatus.FOUND);

//		return optionalVertex.get().asVertex(true);
		String string = optionalVertex.get().toJSON(true).toString();
		return string;
	}
}

