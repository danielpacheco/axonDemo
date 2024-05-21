package com.shaqueiq.arcadedbdemo.repository;

import com.arcadedb.graph.Vertex;
import com.arcadedb.query.sql.executor.ResultSet;
import com.arcadedb.remote.RemoteDatabase;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.service.ArcadeDBService;
import com.shaqueiq.arcadedbdemo.service.HospitalAgreggateService;
import org.axonframework.common.AxonConfigurationException;
import org.axonframework.modelling.command.AbstractRepository;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.inspection.AggregateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.Callable;

@Repository
public class DataQueryRepository {

    @Autowired
    ArcadeDBService arcadeDBService;

    public Optional<Vertex> query(String type, String query) {

        RemoteDatabase remoteDatabase = arcadeDBService.getRemoteDatabase();
        ResultSet resultSet = remoteDatabase.query("SQL", STR."select from `\{type}` where name like '%\{query}%'", query);

//        Database localDatabase = arcadeDBService.getLocalDatabase();
//        Cursor<RID> jayMiner = localDatabase.lookupByKey("Author", new String[] { "name", "surname" }, new Object[] { "Jay", "Miner" });
//        while( jayMiner.hasNext() ){
//            System.out.println( "Found Jay! " + jayMiner.next().getProperty("name"));
//        }


        Optional<Vertex> vertex = Optional.empty();
        while (resultSet.hasNext()) {
            vertex = resultSet.next().getVertex();
        }
        return vertex;
    }
}
