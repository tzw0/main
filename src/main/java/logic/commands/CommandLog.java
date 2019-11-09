package logic.commands;

import farmio.Farmio;
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
     * Shows the Users Log.
     * @param farmio the game which stage is set as LOG.
     * @throws FarmioFatalException if simulation file is missing.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Farmer farmer = farmio.getFarmer();
        Log logTaskList = farmer.getLogTaskList();
        ArrayList<String> output = logTaskList.toStringSplitLogArray(logPage);
        farmio.getFrontend().simulate(output, false);
    }
}
