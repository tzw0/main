package logic.commands;

import farmio.Farmio;
import farmio.exceptions.FarmioFatalException;
import gameassets.Farmer;
import logic.usercode.tasks.TaskList;


public class CommandLog extends Command {

    /**
     * Shows the Users Log.
     * @param farmio the game which stage is set as LOG.
     * @throws FarmioFatalException if simulation file is missing.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        //todo obtain log values
        TaskList logTaskList =  Farmer.getLogTaskList();
        //convert to List of Strings
        //todo Implements the log frontEnd
        //farmio.getFrontend().showMenu(farmio.getStorage().getSaveExist(), true);
    }
}
