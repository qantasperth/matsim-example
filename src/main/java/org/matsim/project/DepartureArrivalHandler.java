package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Person;

import java.time.LocalTime;
import java.util.HashMap;

public class DepartureArrivalHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler {

    private final HashMap<Id<Person>, Double> personDepMap = new HashMap<>();

    @Override
    public void handleEvent(PersonDepartureEvent event) {
        System.out.println("Departure event!\nTime: " + clockTime(event.getTime()) + "\nLink: "
                + event.getLinkId()+ "\nPerson: " + event.getPersonId()+ "\n");
        personDepMap.put(event.getPersonId(), event.getTime());
    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {
        System.out.println("Arrival event!\nTime: " + clockTime(event.getTime()) + "\nLink: "
                 + event.getLinkId()+ "\nPerson: " + event.getPersonId()+ "\n");
        System.out.println("Person: " + event.getPersonId());
        System.out.println("Travel time: "
                + clockTime(event.getTime() - personDepMap.get(event.getPersonId())) + "\n");
    }

    private String clockTime(double seconds) {
        LocalTime timeOfDay = LocalTime.ofSecondOfDay((long)seconds);
        return timeOfDay.toString();
    }
}
