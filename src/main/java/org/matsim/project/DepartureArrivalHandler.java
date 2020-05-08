package org.matsim.project;

import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;

import java.time.LocalTime;

public class DepartureArrivalHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler {
    @Override
    public void handleEvent(PersonDepartureEvent event) {
        System.out.println("Departure event!\nTime: " + clockTime(event.getTime()) + "\nLink: "
                + event.getLinkId()+ "\nPerson: " + event.getPersonId()+ "\n");
    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {
        System.out.println("Arrival event!\nTime: " + clockTime(event.getTime()) + "\nLink: "
                + event.getLinkId()+ "\nPerson: " + event.getPersonId()+ "\n");
    }

    private String clockTime(double seconds) {
        LocalTime timeOfDay = LocalTime.ofSecondOfDay((long)seconds);
        return timeOfDay.toString();
    }
}
