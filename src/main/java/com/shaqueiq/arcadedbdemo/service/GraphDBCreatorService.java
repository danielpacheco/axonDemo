package com.shaqueiq.arcadedbdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.arcadedb.remote.RemoteDatabase;
import com.arcadedb.remote.RemoteServer;

import static java.lang.StringTemplate.STR;


@Service
public class GraphDBCreatorService {

	private static final Logger log = LoggerFactory.getLogger(GraphDBCreatorService.class);

	@Value("${arcadeDB.name}")
	private String arcadeDBname;

	@Value("${arcadeDB.port}")
	private int port;

	@Value("${arcadeDB.server}")
	private String serverDomain;

	@Value("${arcadeDB.user}")
	private String user;

	@Value("${arcadeDB.password}")
	private String password;

	private RemoteDatabase database;


	public void create() {
		RemoteServer server = new RemoteServer(serverDomain, port, user, password);
		database = new RemoteDatabase(serverDomain, port, arcadeDBname, user, password);

		if (!server.exists(arcadeDBname)) {
		    server.create(arcadeDBname);
		    log.debug(STR."Database: \{arcadeDBname} created!");
		} else {
			database.drop();
			database.close();
			server.create(arcadeDBname);
			database = new RemoteDatabase(serverDomain, port, arcadeDBname, user, password);
			log.debug(STR."Database: \{arcadeDBname} exists, connected!");
		}
		createUpdateSchema();
	}


	private void createUpdateSchema() {
		String script =
				"""	
				    create vertex type Physician if not exists;
				    create property Physician.name if not exists string;
				    create index if not exists on Physician (name) unique;
				
				    create vertex type Hospital if not exists;
				    create property Hospital.name if not exists string;
				    create index if not exists on Hospital (name) unique;
				
				""";
				log.debug(STR."Vertexes: created");
				database.command("sqlscript", script);

		script =
				"""
				    delete from Physician;
				    delete from Hospital;
				
				    insert into Physician(name) values("Jose Aleman");
				    insert into Physician(name) values("Daniel Pacheco");
				
				    insert into Hospital(name) values("Johns Hopkins");
				    insert into Hospital(name) values("Mayo Clinics");

				""";
		log.debug(STR."Physician and Hospital data: created!");
		database.command("sqlscript", script);

		script =
				"""
				    CREATE EDGE TYPE isAffiliated IF NOT EXISTS;
						    CREATE PROPERTY isAffiliated.`@out` link OF Physician;
						    CREATE PROPERTY isAffiliated.`@in` link OF Hospital;
				
					
				""";

		log.debug(STR."Physician and Hospital edges: created!");
		database.command("sqlscript", script);

		script =
				"""
				CREATE EDGE isAffiliated FROM (SELECT FROM Physician WHERE name = 'Daniel Pacheco') to (SELECT FROM Hospital) IF NOT EXISTS;
				CREATE EDGE isAffiliated FROM (SELECT FROM Physician WHERE name = 'Jose Aleman') to (SELECT FROM Hospital WHERE name = 'Mayo Clinics') IF NOT EXISTS;
				""";

		log.debug(STR."edges data");
		database.command("sqlscript", script);

	}

}
