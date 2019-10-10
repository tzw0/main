package Commands;

import Task.TaskList;
import Actions.Action;
import Actions.plantSeedAction;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulate.PlantSeedSimulation;
import Task.Condition;
import Task.Task;
import UserInterfaces.Ui;

public class TestCommand extends Command {

    public TestCommand(Ui ui, TaskList tasks, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.ui = ui;
        this.tasks = tasks;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }
    @Override
    public void execute() throws FarmioException {
        try {
            Ui ui = new Ui();
            Condition c = Condition.hasSeeds;
            Action plantSeedAction = new plantSeedAction(ui, wheatFarm, chickenFarm, cowFarm);
            Task task = new Task(c, plantSeedAction);
            tasks.addTask(task);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}