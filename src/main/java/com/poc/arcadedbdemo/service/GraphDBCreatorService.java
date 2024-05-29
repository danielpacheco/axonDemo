package com.poc.arcadedbdemo.service;

import com.arcadedb.remote.RemoteServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arcadedb.remote.RemoteDatabase;

import static java.lang.StringTemplate.STR;


@Service
public class GraphDBCreatorService {

	private static final Logger log = LoggerFactory.getLogger(GraphDBCreatorService.class);

	private RemoteDatabase database;

	@Autowired
	private ArcadeDBService arcadeDBService;
	
	public void create() {

		RemoteServer server = arcadeDBService.getServer();
		String arcadeDBname = arcadeDBService.getArcadeDBname();

		if (!server.exists(arcadeDBname)) {
		    server.create(arcadeDBname);
			database = arcadeDBService.getRemoteDatabase();
		    log.debug(STR."Database: \{arcadeDBname } created!");
		} else {
			database = arcadeDBService.getRemoteDatabase();
			database.drop();
			database.close();
			server.create(arcadeDBname);
			database = arcadeDBService.getRemoteDatabase();
			log.debug(STR."Database: \{arcadeDBname } exists, connected!");
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
				
				    create vertex type Practice if not exists;
				    create property Practice.name if not exists string;
				    create index if not exists on Practice (name) unique;
				
				""";
				log.debug(STR."Vertexes: created");
				database.command("sqlscript", script);

		script =
				"""				
				    insert into Physician(name) values("Jose Aleman");
				    insert into Physician(name) values("Daniel Pacheco");
				    insert into Physician(name) values("Oleg Surajev");
				
				    insert into Hospital(name) values("Johns Hopkins");
				    insert into Hospital(name) values("Mayo Clinics");
				    insert into Hospital(name) values("Ronald Reagan UCLA Medical Center");
				
				    insert into Practice(name) values("Direct Primary Care");
				    insert into Practice(name) values("Solo Practice");
				    insert into Practice(name) values("Group Practice");

				""";
		log.debug(STR."Data: created");
		database.command("sqlscript", script);

		script =
				"""
				    CREATE EDGE TYPE isAffiliated IF NOT EXISTS;
						    CREATE PROPERTY isAffiliated.`@out` link OF Physician;
						    CREATE PROPERTY isAffiliated.`@in` link OF Hospital;
				
				    CREATE EDGE TYPE doPractice IF NOT EXISTS;
						    CREATE PROPERTY doPractice.`@out` link OF Physician;
						    CREATE PROPERTY doPractice.`@in` link OF Practice;
				
				""";

		log.debug(STR."Edges: created");
		database.command("sqlscript", script);

		script =
				"""
				CREATE EDGE isAffiliated FROM (SELECT FROM Physician WHERE name = 'Daniel Pacheco') to (SELECT FROM Hospital) IF NOT EXISTS;
				CREATE EDGE isAffiliated FROM (SELECT FROM Physician WHERE name = 'Jose Aleman') to (SELECT FROM Hospital WHERE name = 'Mayo Clinics') IF NOT EXISTS;
				CREATE EDGE isAffiliated FROM (SELECT FROM Physician WHERE name = 'Oleg Surajev') to (SELECT FROM Hospital WHERE name = 'Ronald Reagan UCLA Medical Center') IF NOT EXISTS;
				
				CREATE EDGE doPractice FROM (SELECT FROM Physician WHERE name = 'Jose Aleman') to (SELECT FROM Practice WHERE name = 'Solo Practice') IF NOT EXISTS;
				CREATE EDGE doPractice FROM (SELECT FROM Physician WHERE name = 'Daniel Pacheco') to (SELECT FROM Practice WHERE name = 'Direct Primary Care') IF NOT EXISTS;
				CREATE EDGE doPractice FROM (SELECT FROM Physician WHERE name = 'Oleg Surajev') to (SELECT FROM Practice WHERE name = 'Direct Primary Care') IF NOT EXISTS;
				CREATE EDGE doPractice FROM (SELECT FROM Physician WHERE name = 'Oleg Surajev') to (SELECT FROM Practice WHERE name = 'Group Practice') IF NOT EXISTS;
				""";

		log.debug(STR."Edges data: created");
		database.command("sqlscript", script);

	}

}
