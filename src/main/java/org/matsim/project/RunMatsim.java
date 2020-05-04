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

		// Config config = ConfigUtils.loadConfig( args ) ;
		
		// possibly modify config here

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


		/*
		Id<Link> someLinkId = Id.createLinkId(24);

		for (Id<Link> linkId: scenario.getNetwork().getLinks().keySet()) {
			if (linkId.equals(someLinkId)) {

			}
		}

		Node node3 = scenario.getNetwork().getNodes().get("3");
		Node node13 = scenario.getNetwork().getNodes().get("13");

		Link newlink = scenario.getNetwork().getFactory().createLink(linkId, node3, node13);

		newlink.setCapacity(3600);
		newlink.setFreespeed(27.78);
		newlink.setLength(13000);
		newlink.setNumberOfLanes(1);

		scenario.getNetwork().addLink(newlink);


		 */
		// ---
		
		Controler controler = new Controler( scenario ) ;
		
		// possibly modify controler here

        // controler.addOverridingModule( new OTFVisLiveModule() ) ;
		
		// ---
		// controler.addOverridingModule( new OTFVisLiveModule() ) ;

		controler.run();
	}
	
}
