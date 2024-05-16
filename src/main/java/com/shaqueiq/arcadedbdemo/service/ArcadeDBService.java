package com.shaqueiq.arcadedbdemo.service;

import com.arcadedb.database.Database;
import com.arcadedb.database.DatabaseFactory;
import com.arcadedb.remote.RemoteDatabase;
import com.arcadedb.remote.RemoteServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ArcadeDBService {

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

    private RemoteServer remoteServer;
    private RemoteDatabase remoteDatabase;
    private Database localDatabase;

    public String getArcadeDBname() {
        return arcadeDBname;
    }

    public void setArcadeDBname(String arcadeDBname) {
        this.arcadeDBname = arcadeDBname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerDomain() {
        return serverDomain;
    }

    public void setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RemoteServer getServer() {
        return remoteServer == null ? new RemoteServer(getServerDomain(), getPort(), getUser(), getPassword()) : remoteServer;
    }

    public RemoteDatabase getRemoteDatabase() {
        return remoteDatabase == null ? new RemoteDatabase(getServerDomain(), getPort(), getArcadeDBname(), getUser(), getPassword()) : remoteDatabase;
    }

    public Database getLocalDatabase() {
        if (localDatabase == null) {
            DatabaseFactory databaseFactory = new DatabaseFactory("/databases/mydbdemo");
            localDatabase = databaseFactory.create();
            // new LocalDatabase("", getServerDomain(), getPort(), getArcadeDBname(), getUser(), getPassword());
        }
        return localDatabase;
    }

}
