package UserCode.Actions;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class GotoWheatFarmAction extends Action {

    public GotoWheatFarmAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.gotoWheatFarm;
    }

    public void execute(Ui ui) throws FarmioException {
        try {
            Simulation GotoWheatFarmSimulation = new Simulation("GotoWheatFarmSimulation", super.farmio);
            if (farmer.getLocation().equals("WheatFarm")) {
                GotoWheatFarmSimulation.delayFrame(12, 1000);
                ui.typeWriter("You are already at the WheatFarm");
                return;
            }
            farmer.changeLocation("Traveling");
            GotoWheatFarmSimulation.animate(1, 11);
            farmer.changeLocation("WheatFarm");
            GotoWheatFarmSimulation.delayFrame(12, 1000);
            ui.typeWriter("You have arrived at the WheatFarm");
        } catch (Exception e){
            e.getMessage();
        }
    }
}
