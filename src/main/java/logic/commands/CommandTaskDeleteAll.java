package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Frontend;

public class CommandTaskDeleteAll extends Command {

    /**
     * Delete all tasks in the tasklist.
     * @param farmio the game that contains the tasklist to be cleared.
     * @throws FarmioFatalException if the simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Frontend frontend = farmio.getFrontend();
        farmio.getFarmer().deleteAll();
        frontend.simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
        frontend.showInfo("You have deleted all tasks!");
    }
}
