package UserCode.Actions;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Places.Market;

public class BuySeedAction extends Action {

    public BuySeedAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.buySeeds;
    }

    @Override
    public void execute(Ui ui) throws FarmioException {
        if (farmer.getMoney() < Market.PRICE_OF_SEED || !farmer.getLocation().equals("Market")) {
            farmio.getFarmer().setfailetask();
            new Simulation("ErrorInExecution", farmio).animate(0);
            if (!farmer.getLocation().equals("Market")) {
                ui.typeWriter("Error! you have attempted to buy seeds despite not being at the market\n");
            } else {
                ui.typeWriter("Error! you have attempted to buy seeds despite not having enough money\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            new Simulation("BuySeedSimulation", farmio).animate(0, 4);
            farmer.getWheatFarm().buySeeds();
            farmer.changeMoney(-Market.PRICE_OF_SEED);
            new Simulation("BuySeedSimulation", farmio).delayFrame(5, 1000);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}