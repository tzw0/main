package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import UserInterfaces.Ui;

public class ForTask extends Task { //JX PLEASE FILL THIS SHIT UP

    public ForTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Ui ui) { //if got error during the loop we use FarmioException to break out
        {
            int repeatNumber = 0;
            for (int i = 0; i < repeatNumber; i ++) {
                action.execute(ui);
            }
        }
    }
}