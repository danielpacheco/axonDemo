package com.shaqueiq.arcadedbdemo.model;

import com.arcadedb.graph.Vertex;
import com.shaqueiq.arcadedbdemo.cqrs.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.AffiliateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class Hospital implements Constants {

    @AggregateIdentifier
    private String id;
    private String name;

    //    _______________________

    protected Hospital() {}

    public Hospital(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hospital(String name) {
        this.name = name;
    }

    //    _______________________

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    _______________________

    @CommandHandler
    public Hospital(AffiliateCommand cmd) {
        apply(new AffiliateEvent(cmd.getId(), cmd.getPhysician()));
    }

    public static Hospital fromVertex(Vertex vertex) {
        return new Hospital(vertex.getString(NAME));
    }

    @EventSourcingHandler
    public void on(AffiliateEvent evt) {
        id = evt.getId();
    }

//    @EventSourcingHandler
//    private void handleCreatedEvent(LibraryCreatedEvent event) {
//        libraryId = event.getLibraryId();
//        name = event.getName();
//        isbnBooks = new ArrayList<>();
//    }

}
