package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunEventsHandler {

    public static void main(String[] args) {

        String inputFile = "output/output_events.xml.gz";

        EventsManager eventsManager = EventsUtils.createEventsManager();

        DepartureArrivalHandler departureArrivalHandler = new DepartureArrivalHandler();
        TravelTimeHandler travelTimeHandler = new TravelTimeHandler();

        eventsManager.addHandler(departureArrivalHandler);
        eventsManager.addHandler(travelTimeHandler);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(inputFile);

        travelTimeHandler.writeTravelTime(Id.createPersonId(1));
        travelTimeHandler.writeTravelTime(Id.createPersonId("Timur"));
        travelTimeHandler.averageTravelTime();
    }
}

