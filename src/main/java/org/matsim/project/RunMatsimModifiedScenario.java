/* *********************************************************************** *
 * project: org.matsim.*												   *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.*;
import org.matsim.contrib.otfvis.OTFVisLiveModule;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nagel
 *
 */

public class RunMatsimModifiedScenario {

    public static void main(String[] args) {

        // basic config declaration:
        // Config config = ConfigUtils.loadConfig(args);

        // take config, overwrite output directory in project folder
        Config config;
        if (args == null || args.length == 0 || args[0] == null) {
            config = ConfigUtils.loadConfig("scenarios/equil/config.xml");
        } else {
            config = ConfigUtils.loadConfig(args);
        }
        config.controler().setOverwriteFileSetting(
                OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);

        config.controler().setLastIteration(2);

        // ---

        Scenario scenario = ScenarioUtils.loadScenario(config);

        // possibly modify scenario here:

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

        // adding new link between 2 nodes
        Id<Node> IdNode1 = Id.createNodeId(3);
        Id<Node> IdNode2 = Id.createNodeId(14);

        Node node1 = scenario.getNetwork().getNodes().get(IdNode1);
        Node node2 = scenario.getNetwork().getNodes().get(IdNode2);
        Id<Link> IdLink = Id.createLinkId(24);

        Link newlink = scenario.getNetwork().getFactory().createLink(IdLink, node1, node2);

        newlink.setCapacity(3600);
        newlink.setFreespeed(27.78);
        newlink.setLength(12000);
        newlink.setNumberOfLanes(1);

        scenario.getNetwork().addLink(newlink);

        // ---

        Controler controler = new Controler(scenario);

        // possibly modify controler here:
        // ---

        controler.run();

        System.out.println("Iterations made: " + config.controler().getLastIteration());
    }
}
