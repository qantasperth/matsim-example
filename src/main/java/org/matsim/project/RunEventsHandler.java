package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunEventsHandler {

    public static void main(String[] args) {

        String inputFile = "output/output_events.xml.gz";

        EventsManager eventsManager = EventsUtils.createEventsManager();


        SimpleEventHandler eventHandler = new SimpleEventHandler();
        SecondEventHandler eventHandler2 = new SecondEventHandler();

        eventsManager.addHandler(eventHandler);
        eventsManager.addHandler(eventHandler2);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(inputFile);

        eventHandler2.writeTravelTime(Id.createPersonId(1));
        eventHandler2.writeTravelTime(Id.createPersonId("Timur"));
        eventHandler2.averageTravelTime();
    }
}

