package org.matsim.project;


import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.network.io.NetworkWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkModifier {

    public static void main(String[] args) {

        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(input.toString());

        for (int i=5; i<9; i++) {
            network.getLinks().get(Id.createLinkId(i)).setCapacity(1);
        }

        new NetworkWriter(network).write(output.toString());

    }
}
