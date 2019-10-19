package UserCode.Actions;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Places.Farm;

public class GotoMarketAction extends Action {
    public GotoMarketAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.gotoMarket;
    }
    @Override
    public void execute(Ui ui) throws FarmioException {
        try {
            Simulation GotoMarketSimulation = new Simulation("GotoMarketSimulation", super.farmio);
            if (farmer.getLocation().equals("Market")) {
                GotoMarketSimulation.delayFrame(12, 1000);
                ui.typeWriter("You are already at the market");
                return;
            }
            farmer.changeLocation("Traveling");
            GotoMarketSimulation.animate(1, 11);
            farmer.changeLocation("Market");
            GotoMarketSimulation.delayFrame(12, 1000);
            ui.typeWriter("You have arrived at the market");
        } catch (Exception e){
            e.getMessage();
        }
    }
}

