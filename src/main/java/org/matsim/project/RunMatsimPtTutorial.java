package org.matsim.project;

import ch.sbb.matsim.routing.pt.raptor.SwissRailRaptorModule;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.*;
import org.matsim.pt.utils.TransitScheduleValidator;
import org.matsim.vehicles.Vehicle;
import org.matsim.vehicles.VehicleType;
import org.matsim.vehicles.VehiclesFactory;


@SuppressWarnings("rawtypes")
public class RunMatsimPtTutorial {

    private static final Logger LOGGER = Logger.getLogger(RunMatsim.class);

    public static void main(String[] args) {

        // config section
        Config config = ConfigUtils.loadConfig("scenarios/pt-tutorial/0.config.xml");

        config.plans().setInputFile("population.xml");
        config.controler().setOverwriteFileSetting(
                OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
        config.controler().setOutputDirectory("./output-pt-tutorial");
        config.controler().setLastIteration(0);


        // scenario section
        Scenario scenario = ScenarioUtils.loadScenario(config);

        TransitSchedule transitSchedule = scenario.getTransitSchedule();
        TransitLine blueLine = transitSchedule.getTransitLines().get(Id.create("Blue Line", TransitLine.class));
        TransitRoute route1to3 = blueLine.getRoutes().get(Id.create("1to3", TransitRoute.class));

        TransitScheduleFactory tsf = transitSchedule.getFactory();
        Departure departure = tsf.createDeparture(Id.create("newDep", Departure.class), (8*60.+45) * 60.);

        route1to3.addDeparture(departure);

        TransitScheduleValidator.ValidationResult result = TransitScheduleValidator.validateAll(transitSchedule, scenario.getNetwork());
        for (String error : result.getWarnings()) {
            LOGGER.warn(error);
        }
        for (String warning : result.getWarnings()) {
            LOGGER.warn(warning);
        }
        for (TransitScheduleValidator.ValidationResult.ValidationIssue issue : result.getIssues()) {
            LOGGER.warn(issue.getMessage());
        }

        VehiclesFactory vf = scenario.getVehicles().getFactory();
        VehicleType smallTrain = scenario.getTransitVehicles().getVehicleTypes().get(Id.create("1", VehicleType.class));
        Vehicle vehicle = vf.createVehicle(Id.createVehicleId("tr_3"), smallTrain);
        scenario.getTransitVehicles().addVehicle(vehicle);
        departure.setVehicleId(vehicle.getId());

        TransitScheduleWriter tsw = new TransitScheduleWriter(transitSchedule);
        tsw.writeFile("./scenarios/pt-tutorial/transitschedule-modified.xml");


        // controler section
        Controler controler = new Controler(scenario);
        controler.addOverridingModule(new SwissRailRaptorModule());
        controler.run();
    }
}
