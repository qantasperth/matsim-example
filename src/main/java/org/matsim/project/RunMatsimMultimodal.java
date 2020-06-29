package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.collections.CollectionUtils;
import org.matsim.vehicles.VehicleType;
import org.matsim.vehicles.VehiclesFactory;

import java.util.Objects;

// this class introduces new mode "pedelec"
public class RunMatsimMultimodal {

    public static void main(String[] args) {

        // config section
        Config config = ConfigUtils.loadConfig("scenarios/equil/config.xml");

        config.plansCalcRoute().setNetworkModes(CollectionUtils.stringToSet("car,pedelec"));

        config.qsim().setMainModes(CollectionUtils.stringToSet("car,pedelec"));
        config.qsim().setVehiclesSource(QSimConfigGroup.VehiclesSource.modeVehicleTypesFromVehiclesData);
        config.qsim().setLinkDynamics(QSimConfigGroup.LinkDynamics.PassingQ);

        PlanCalcScoreConfigGroup.ModeParams modeParams = new PlanCalcScoreConfigGroup.ModeParams("pedelec");
        modeParams.setConstant(100.);
        config.planCalcScore().addModeParams(modeParams);

        config.controler().setLastIteration(5);
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);

        // scenario section
        Scenario scenario = ScenarioUtils.loadScenario(config);

        for (Link link : scenario.getNetwork().getLinks().values()) {
            link.setAllowedModes(CollectionUtils.stringToSet("car,pedelec"));
        }

        for (int i=2; i<20; i++) {
            if (i==6 || i==15) continue;
            Link link = scenario.getNetwork().getLinks().get(Id.createLinkId(i));
            Objects.requireNonNull(link);
            link.setAllowedModes(CollectionUtils.stringToSet("pedelec"));
        }

        VehiclesFactory vf = scenario.getVehicles().getFactory();
        VehicleType vehicleTypePedelec = vf.createVehicleType(Id.create("pedelec", VehicleType.class));
        vehicleTypePedelec.setMaximumVelocity(10.);
        scenario.getVehicles().addVehicleType(vehicleTypePedelec);

        VehicleType vehicleTypeCar = vf.createVehicleType(Id.create("car", VehicleType.class));
        scenario.getVehicles().addVehicleType(vehicleTypeCar);

        // controler section
        Controler controler = new Controler(scenario);
        controler.run();

    }

}
