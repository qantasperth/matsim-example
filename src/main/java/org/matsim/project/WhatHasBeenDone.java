package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.*;

import java.util.ArrayList;
import java.util.List;

public class WhatHasBeenDone {

    public void addLinkBetweenTwoNodes(Scenario scenario, long nodeId1, long nodeId2, long linkId) {

        Id<Node> IdNode1 = Id.createNodeId(nodeId1);
        Id<Node> IdNode2 = Id.createNodeId(nodeId2);

        Node node1 = scenario.getNetwork().getNodes().get(IdNode1);
        Node node2 = scenario.getNetwork().getNodes().get(IdNode2);
        Id<Link> IdLink = Id.createLinkId(linkId);

        Link newlink = scenario.getNetwork().getFactory().createLink(IdLink, node1, node2);

        scenario.getNetwork().addLink(newlink);
    }

    public void setLinkParams(Scenario scenario, long id, double capa, double speed, double length, double numLanes) {

        Link link = scenario.getNetwork().getLinks().get(Id.createLinkId(id));

        link.setCapacity(capa);
        link.setFreespeed(speed);
        link.setLength(length);
        link.setNumberOfLanes(numLanes);
    }

    public void removingAgentsAddingTimur(Scenario scenario) {

        // delete all people except person id 1
        Id<Person> somePerson = Id.createPersonId(1);
        List<Id<Person>> personToRemove = new ArrayList<>();
        for (Id<Person> personId : scenario.getPopulation().getPersons().keySet()) {
            if (!personId.equals(somePerson)) {
                personToRemove.add(personId);
            }
        }
        for (Id<Person> personId : personToRemove) {
            scenario.getPopulation().removePerson(personId);
        }
        System.out.println("Population size: " + scenario.getPopulation().getPersons().size());

        // adding new agent with a plan
        PopulationFactory popFactory = scenario.getPopulation().getFactory();

        Person timur = popFactory.createPerson(Id.createPersonId("Timur"));
        Plan plan = popFactory.createPlan();

        Activity homeAct = popFactory.createActivityFromLinkId("h", Id.createLinkId(21));
        homeAct.setEndTime(8 * 60 * 60);
        plan.addActivity(homeAct);

        Leg leg1 = popFactory.createLeg(TransportMode.car);
        plan.addLeg(leg1);

        Activity workAct = popFactory.createActivityFromLinkId("w", Id.createLinkId(1));
        workAct.setEndTime(17 * 60 * 60);
        plan.addActivity(workAct);

        Leg leg2 = popFactory.createLeg(TransportMode.car);
        plan.addLeg(leg2);

        timur.addPlan(plan);
        scenario.getPopulation().addPerson(timur);
        System.out.println("Population size: " + scenario.getPopulation().getPersons().size());
    }
}
