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

public class RunMatsim{

	public static void main(String[] args) {

		// Config config = ConfigUtils.loadConfig( args ) ;        basic config declaration

		Config config;
		if ( args==null || args.length==0 || args[0]==null ){
			config = ConfigUtils.loadConfig( "scenarios/equil/config.xml" );
		} else {
			config = ConfigUtils.loadConfig( args );
		}
		config.controler().setOverwriteFileSetting(
				OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists );

		// ---

		Scenario scenario = ScenarioUtils.loadScenario(config) ;
		
		// possibly modify scenario here

		new WhatHasBeenDone().addLinkBetweenTwoNodes(scenario, 11, 14, 24);

		// delete all people except person id 1
		Id<Person> somePerson = Id.createPersonId(1);
		List<Id<Person>> personToRemove = new ArrayList<>();
		for (Id<Person> personId: scenario.getPopulation().getPersons().keySet()) {
			if (!personId.equals(somePerson)) {
				personToRemove.add(personId);
			}
		}
		for (Id<Person> personId: personToRemove) {
			scenario.getPopulation().removePerson(personId);
		}
		System.out.println("Population size: " + scenario.getPopulation().getPersons().size());

		// adding new agent with a plan
		PopulationFactory popFactory = scenario.getPopulation().getFactory();

		Person timur = popFactory.createPerson(Id.createPersonId("Timur"));
		Plan plan = popFactory.createPlan();

		Activity homeAct = popFactory.createActivityFromLinkId("h", Id.createLinkId(21));
		homeAct.setEndTime(8*60*60);
		plan.addActivity(homeAct);

		Leg leg1 = popFactory.createLeg(TransportMode.car);
		plan.addLeg(leg1);

		Activity workAct = popFactory.createActivityFromLinkId("w", Id.createLinkId(1));
		workAct.setEndTime(17*60*60);
		plan.addActivity(workAct);

		Leg leg2 = popFactory.createLeg(TransportMode.car);
		plan.addLeg(leg2);

		timur.addPlan(plan);
		scenario.getPopulation().addPerson(timur);
		System.out.println("Population size: " + scenario.getPopulation().getPersons().size());

		// ---
		
		Controler controler = new Controler(scenario) ;
		
		// possibly modify controler here
		// controler.addOverridingModule( new OTFVisLiveModule() );          doesn't work on OSX 10.14 either
		// ---

		controler.run();
	}
	
}
