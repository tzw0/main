package logic.usercode.actions;

import farmio.exceptions.FarmioFatalException;
import frontend.Frontend;
import gameassets.Farmer;
import storage.Storage;

public class GotoFarmAction extends Action {

    public GotoFarmAction() {
        super(ActionType.gotoWheatFarm);
    }

    /**
     * Executes the action of going to the farm.
     * @param frontend The user interface used to print messages of the action
     * @param storage which stores the assets after acton execution
     * @param farmer The farmer whose variables are displayed and changed
     * @throws FarmioFatalException Fatel error from simulation, need to stop program.
     */
    public void execute(Frontend frontend, Storage storage, Farmer farmer) throws FarmioFatalException {
        if (farmer.getLocation().equals("WheatFarm")) {
            frontend.simulate("GotoWheatFarmSimulation", 12);
            frontend.typeWriter("You are already at the WheatFarm", false);
        } else {
            farmer.changeLocation("Traveling");
            frontend.simulate("GotoWheatFarmSimulation", 1, 12);
            farmer.changeLocation("WheatFarm");
            frontend.typeWriter("You have arrived at the WheatFarm", false);
        }
        frontend.sleep(200);
    }
}
