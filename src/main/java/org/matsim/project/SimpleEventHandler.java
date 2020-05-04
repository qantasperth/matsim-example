package org.matsim.project;

import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;

public class SimpleEventHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler {
    @Override
    public void handleEvent(PersonDepartureEvent event) {
        System.out.println("Departure event!\nTime: " + event.getTime() + "\nLink: " + event.getLinkId()+ "\nPerson: " + event.getPersonId()+ "\n");
    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {
        System.out.println("Arrival event!\nTime: " + event.getTime() + "\nLink: " + event.getLinkId()+ "\nPerson: " + event.getPersonId()+ "\n");
    }
}
