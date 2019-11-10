package logic.usercode.actions;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import frontend.Frontend;
import gameassets.Farmer;
import storage.Storage;
import javafx.util.Pair;

import java.util.ArrayList;

public class SellGrainAction extends Action {

    public SellGrainAction() {
        super(ActionType.sellGrain);
    }

    @Override
    public void execute(Frontend frontend, Storage storage, Farmer farmer)
            throws FarmioFatalException, FarmioException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(!farmer.hasGrain(),
                "Error! you have attempted to sell grain despite not having any grain"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("Market"),
                "Error! you have attempted to sell grain despite not being at the market"));
        checkActionCriteria(frontend, farmer, criteriaFeedbackList);
        frontend.simulate("SellWheatSimulation", 0, 9);
        frontend.typeWriter("Selling grain!", false);
        frontend.sleep(200);
        farmer.earnGold(farmer.sellGrain());
        frontend.simulate();
        frontend.sleep(200);
    }
}