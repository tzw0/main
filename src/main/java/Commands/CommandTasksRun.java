package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandTasksRun extends Command {
    @Override
    public void execute(Farmio farmio) {
        try {
            farmio.getFarmer().startDay(farmio);
        } catch (FarmioException e) {
            //Todo Failure in Tasklist, player has failed the objective, resetting to saved farmer
            farmio.getUi().show("Code Failed! resetting assets..");
        }
        farmio.getUi().show("End of Day!");
        farmio.setStage(Farmio.Stage.CHECK_OBJECTIVES);
    }
}