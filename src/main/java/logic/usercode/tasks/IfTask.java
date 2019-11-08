package logic.usercode.tasks;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.exceptions.FarmioException;
import logic.usercode.actions.Action;
import logic.usercode.conditions.Condition;

public class IfTask extends Task {

    /**
     * Creates a task of type if.
     * @param condition The condition to be considered.
     * @param action The action to be executed if the condition is true.
     */
    public IfTask(Condition condition, Action action) {
        super(Tasktype.IF, condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        {
            if (condition.check(farmio)) {
                action.execute(farmio.getFrontend(), farmio.getStorage(), farmio.getFarmer());
            } else {
                farmio.getFrontend().simulate();
                farmio.getFrontend().show("Condition not fulfilled, not executing task!");
                farmio.getFrontend().sleep(1000);
            }
        }
    }

    @Override
    public String toString() {
        return "if " + super.toString();
    }
}