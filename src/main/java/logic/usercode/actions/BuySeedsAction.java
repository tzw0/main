package logic.usercode.actions;

import farmio.Farmio;
import farmio.exceptions.FarmioFatalException;
import frontend.Frontend;
import gameassets.Farmer;
import storage.Storage;
import farmio.exceptions.FarmioException;
import gameassets.places.Market;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.logging.Level;

public class BuySeedsAction extends Action {

    public BuySeedsAction() {
        super(ActionType.buySeeds);
    }

    @Override
    public void execute(Frontend frontend, Storage storage, Farmer farmer)
            throws FarmioException, FarmioFatalException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(farmer.getGold() < Market.PRICE_OF_SEED,
                "Error! you have attempted to buy seeds despite not having enough money"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("Market"),
                "Error! you have attempted to buy seeds despite not being at the market"));
        checkActionCriteria(frontend, farmer, criteriaFeedbackList);
        frontend.simulate("BuySeedSimulation", 0, 9);
        frontend.typeWriter("Buying Seeds!", false);
        farmer.buySeeds();
        farmer.spendGold(Market.PRICE_OF_SEED);
        frontend.simulate();
        frontend.sleep(200);
    }
}
