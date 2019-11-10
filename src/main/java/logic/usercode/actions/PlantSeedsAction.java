package logic.usercode.actions;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import frontend.Frontend;
import gameassets.Farmer;
import storage.Storage;
import javafx.util.Pair;

import java.util.ArrayList;

public class PlantSeedsAction extends Action {

    public PlantSeedsAction() {
        super(ActionType.plantSeeds);
    }

    @Override
    public void execute(Frontend frontend, Storage storage, Farmer farmer)
            throws FarmioFatalException, FarmioException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(!farmer.hasSeeds(),
                "Error! you have attempted to plant seeds despite not having any seeds"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("WheatFarm"),
                "Error! you have attempted to plant seeds despite not being at the Wheatfarm"));
        checkActionCriteria(frontend, farmer, criteriaFeedbackList);
        frontend.simulate("PlantSeedSimulation", 0, 11);
        frontend.typeWriter("Planting Seeds!", false);
        farmer.plantSeeds();
        frontend.simulate();
        frontend.sleep(200);
    }
}