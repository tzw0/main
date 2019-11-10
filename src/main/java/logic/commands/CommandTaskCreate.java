package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Frontend;
import gameassets.Farmer;
import farmio.exceptions.FarmioException;
import logic.usercode.tasks.Task;

public class CommandTaskCreate extends Command {
    private Task task;

    public CommandTaskCreate(Task task) {
        this.task = task;
    }

    /**
     * Creating a Task based on the interpretation of the parser.
     * @param farmio the game that contains the tasklist that is being changed.
     * @throws FarmioException if action is executed although its criteria is not met.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Frontend frontend = farmio.getFrontend();
        Farmer farmer = farmio.getFarmer();
        farmer.addTask(task);
        frontend.simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
        frontend.showInfo("Task [" + task.toString() + "] added! \nYou now have "
                + farmer.getTasks().size() + " tasks!");
    }
}
