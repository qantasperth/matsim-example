package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Person;

import java.time.LocalTime;
import java.util.HashMap;

public class TravelTimeHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler {

    private final HashMap<Id<Person>, Double> personTimeMap = new HashMap<>();

    @Override
    public void handleEvent(PersonDepartureEvent event) {
        if (!personTimeMap.containsKey(event.getPersonId())) {
            personTimeMap.put(event.getPersonId(), -event.getTime());
        } else {
            personTimeMap.put(event.getPersonId(), personTimeMap.get(event.getPersonId()) - event.getTime());
        }
    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {
        if (!personTimeMap.containsKey(event.getPersonId())) {
            personTimeMap.put(event.getPersonId(), event.getTime());
        } else {
            personTimeMap.put(event.getPersonId(), personTimeMap.get(event.getPersonId()) + event.getTime());
        }
    }

    public void writeTravelTime(Id<Person> personId) {
        System.out.println("Agent: " + personId.toString());
        System.out.println("Total time travelled: " + clockTime(personTimeMap.get(personId)) + "\n");
    }

    public void averageTravelTime() {
        double totalTime = 0.0;
        for (Id<Person> agent: personTimeMap.keySet()) {
            totalTime += personTimeMap.get(agent);
        }
        double averageTime = totalTime / personTimeMap.keySet().size();
        System.out.println("Average time travelled per agent: " + clockTime(averageTime) + "\n");
    }

    private String clockTime(double seconds) {
        LocalTime timeOfDay = LocalTime.ofSecondOfDay((long)seconds);
        return timeOfDay.toString();
    }
}
