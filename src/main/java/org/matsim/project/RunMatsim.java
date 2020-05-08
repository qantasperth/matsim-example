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

public class RunMatsim {

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

		// WhatHasBeenDone class contains some stuff I did zB:
		// new WhatHasBeenDone().addLinkBetweenTwoNodes(scenario, 11, 14, 24);
		// new WhatHasBeenDone().setLinkParams(scenario, 24, 3600, 27.78, 10000, 1);
		new WhatHasBeenDone().removingAgentsAddingTimur(scenario);

		// ---

		Controler controler = new Controler(scenario);

		// possibly modify controler here:
		// controler.addOverridingModule( new OTFVisLiveModule() );          // doesn't work for my OSX 10.14 either
		// ---

		controler.run();

		System.out.println("Iterations made: " + config.controler().getLastIteration());
	}
}
