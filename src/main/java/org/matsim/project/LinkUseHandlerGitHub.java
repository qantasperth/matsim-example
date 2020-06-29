package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.core.utils.charts.XYLineChart;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalTime;

public class LinkUseHandlerGitHub implements LinkEnterEventHandler {

    // todo: hashmap (link, double) for any link

    double[] volumeOnLink6 = new double[24];

    @Override
    public void handleEvent(LinkEnterEvent event) {
        if (event.getLinkId() == Id.createLinkId(6)) {
            this.volumeOnLink6[(int)(event.getTime()/3600)]++;
        }
    }

    // todo: also try writing result to text file instead of png chart

    public void drawChart(String filename) {
        double[] hours = new double[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = i;
        }
        XYLineChart chart = new XYLineChart("Traffic on link 6", "hours", "vehicle enters");
        chart.addSeries("times", hours, volumeOnLink6);
        chart.addMatsimLogo();
        chart.saveAsPng(filename, 800, 600);
    }
}
