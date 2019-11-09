package logic.commands;

import farmio.Farmio;
import farmio.exceptions.FarmioFatalException;
import gameassets.Farmer;
import gameassets.Log;
import logic.usercode.tasks.TaskList;

import java.util.List;


public class CommandLog extends Command {

    /**
     * Shows the Users Log.
     * @param farmio the game which stage is set as LOG.
     * @throws FarmioFatalException if simulation file is missing.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Farmer farmer = farmio.getFarmer();
        Log logTaskList = farmer.getLogTaskList();
        List<String> output = logTaskList.toStringArray();


        for (String i : output) {
            farmio.getFrontend().typeWriter(i,false); //test for log
        }


        //todo Implements the log frontEnd
        //farmio.getFrontend().showMenu(farmio.getStorage().getSaveExist(), true);
    }
}
