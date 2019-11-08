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
        Log logTaskList = farmer.getLogTaskList();//convert to List of Strings
        //todo convertTaskList to String Format.
        List<String> output = logTaskList.toStringArray();

        //todo test output of log
        for (String i : output) {
            farmio.getFrontend().typeWriter(i,false);
        }


        //todo Implements the log frontEnd
        //farmio.getFrontend().showMenu(farmio.getStorage().getSaveExist(), true);
    }
}
