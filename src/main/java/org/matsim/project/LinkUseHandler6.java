package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.core.utils.charts.XYLineChart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LinkUseHandler6 implements LinkEnterEventHandler {

    private int[] volumeOnLink6 = new int[24];
    private final BufferedWriter writer;

    public LinkUseHandler6(String outputFile) {
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            writer = new BufferedWriter(fileWriter);
        } catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }

    @Override
    public void handleEvent(LinkEnterEvent event) {
        if (event.getLinkId().equals(Id.createLinkId(6))) {
            int hour = (int)event.getTime()/3600;
            this.volumeOnLink6[hour]++;
        }
    }

    public void write() {
        for (int i=0; i < 24; i++) {
            System.out.println("Link enters at the time of " + i + ": " + this.volumeOnLink6[i]);
        }
    }


    public void writeToFile() {
        try {
            writer.write("Hour \t Volume");
            for (int i=0; i < 24; i++) {
                writer.write("\n" + i + "\t\t\t" + this.volumeOnLink6[i]);
            }
            writer.close();
        } catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }
}
