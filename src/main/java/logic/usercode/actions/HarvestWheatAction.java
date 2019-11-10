package logic.usercode.actions;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import frontend.Frontend;
import gameassets.Farmer;
import storage.Storage;
import javafx.util.Pair;

import java.util.ArrayList;

public class HarvestWheatAction extends Action {

    public HarvestWheatAction() {
        super(ActionType.harvestWheat);
    }

    @Override
    public void execute(Frontend frontend, Storage storage, Farmer farmer)
            throws FarmioFatalException, FarmioException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(!farmer.hasWheat(),
                "Error! you have attempted to harvest wheat despite not having any wheat"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("WheatFarm"),
                "Error! you have attempted to harvest wheat despite not being at the Wheatfarm"));
        checkActionCriteria(frontend, farmer, criteriaFeedbackList);
        frontend.simulate("HarvestWheatSimulation", 0, 9);
        farmer.harvestWheat();
        frontend.simulate();
        frontend.sleep(200);
    }
}