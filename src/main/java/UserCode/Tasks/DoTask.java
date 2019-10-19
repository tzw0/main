package UserCode.Tasks;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import Places.Farm;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import FrontEnd.Ui;

public class DoTask extends Task {

    public DoTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException {
        {
            action.execute(farmio.getUi());
        }
    }

    @Override
    public String toString() {
        return "do " + action.toString();
    }
}