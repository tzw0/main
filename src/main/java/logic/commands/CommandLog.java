package logic.commands;

import farmio.Farmio;
import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import gameassets.Farmer;
import gameassets.Log;
import java.util.ArrayList;


public class CommandLog extends Command {
    int logPage;

    public CommandLog(int logPage) {
        this.logPage = logPage;
    }

    /**
     * Shows the User's Log.
     * @param farmio the game which stage is set as LOG.
     * @throws FarmioFatalException if the simulation file is missing
     * @throws FarmioException if the user keys in an invalid lof page
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException, FarmioException {
        Farmer farmer = farmio.getFarmer();
        double level = farmio.getFarmer().getLevel();
        Log logTaskList = farmer.getLogTaskList();
        ArrayList<String> output = logTaskList.toStringSplitLogArray(logPage,level);
        farmio.getFrontend().simulate(output, false);
        farmio.getFrontend().typeWriter("Press [Enter] to go back",false);
    }
}
