package logic.commands;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Frontend;

public class CommandTaskDelete extends Command {
    int taskID;

    public CommandTaskDelete(int taskID) {
        this.taskID = taskID;
    }

    /**
     * Delete a specific task from the task list.
     * @param farmio the game with the tasklist to be editted.
     * @throws FarmioException if TaskID is invalid, or if there is error deleting task.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Frontend frontend = farmio.getFrontend();
        if (taskID < 1 || taskID > farmio.getFarmer().taskSize()) {
            throw new FarmioException("Invalid TaskID!");
        }
        try {
            String taskToString = farmio.getFarmer().deleteTask(taskID);
            frontend.simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
            frontend.showInfo("You have deleted task: " + taskToString);
        } catch (Exception e) {
            throw new FarmioException("Error deleting task!");
        }
    }
}
