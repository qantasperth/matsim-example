package org.matsim.project;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunEventsHandler {

    public static void main(String[] args) {

        String inputFile = "output/output_events.xml.gz";

        EventsManager eventsManager = EventsUtils.createEventsManager();

        // DepartureArrivalHandler departureArrivalHandler = new DepartureArrivalHandler();
        // eventsManager.addHandler(departureArrivalHandler);

        TravelTimeHandler travelTimeHandler = new TravelTimeHandler();
        eventsManager.addHandler(travelTimeHandler);

        LinkUseHandler6 linkUseHandler6 = new LinkUseHandler6();
        eventsManager.addHandler(linkUseHandler6);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(inputFile);

        linkUseHandler6.drawChart("traffic link 6");
        // linkUseHandler6.numberEntersByTime(21622.5);
        // linkUseHandler6.numberEntersByTime(25000.0);
        // travelTimeHandler.writeTravelTime(Id.createPersonId(1));
        // travelTimeHandler.writeTravelTime(Id.createPersonId(2));
        // travelTimeHandler.averageTravelTime();
    }
}

