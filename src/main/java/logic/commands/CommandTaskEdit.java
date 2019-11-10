package logic.commands;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Frontend;
import logic.usercode.tasks.Task;
import gameassets.Farmer;

public class CommandTaskEdit extends Command {
    private Task task;
    private int taskID;


    public CommandTaskEdit(int taskID, Task task) {
        this.taskID = taskID;
        this.task = task;
    }

    /**
     * Edit a Task in the tasklist.
     * @param farmio the game which contains the tasklist to be editted.
     * @throws FarmioFatalException if the simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Farmer farmer = farmio.getFarmer();
        Frontend frontend = farmio.getFrontend();
        if (taskID < 1 || taskID > farmer.taskSize()) {
            throw new FarmioException("Invalid Task ID!");
        }
        farmer.editTask(taskID, task);
        frontend.simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
        frontend.showInfo("Successfully edited task!");
    }
}
