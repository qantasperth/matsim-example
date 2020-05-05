package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;

public class WhatHasBeenDone {

    public static void main(String[] args) {
    }

    public void addLink_3_13(Scenario scenario) {

        Id<Node> nodeId3 = Id.createNodeId(3);
        Id<Node> nodeId13 = Id.createNodeId(13);

        Node node3 = scenario.getNetwork().getNodes().get(nodeId3);
        Node node13 = scenario.getNetwork().getNodes().get(nodeId13);
        Id<Link> linkId = Id.createLinkId(24);

        Link newlink = scenario.getNetwork().getFactory().createLink(linkId, node3, node13);

        newlink.setCapacity(3600);
        newlink.setFreespeed(27.78);
        newlink.setLength(13000);
        newlink.setNumberOfLanes(1);

        scenario.getNetwork().addLink(newlink);
    }

    public void addLinkBetweenTwoNodes(Scenario scenario, long nodeId1, long nodeId2, long linkId) {

        Id<Node> IdNode1 = Id.createNodeId(nodeId1);
        Id<Node> IdNode2 = Id.createNodeId(nodeId2);

        Node node1 = scenario.getNetwork().getNodes().get(IdNode1);
        Node node2 = scenario.getNetwork().getNodes().get(IdNode2);
        Id<Link> IdLink = Id.createLinkId(linkId);

        Link newlink = scenario.getNetwork().getFactory().createLink(IdLink, node1, node2);

        newlink.setCapacity(3600);
        newlink.setFreespeed(27.78);
        newlink.setLength(13000);
        newlink.setNumberOfLanes(1);

        scenario.getNetwork().addLink(newlink);
    }

}
