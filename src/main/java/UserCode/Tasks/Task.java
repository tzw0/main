package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Actions.PlantSeedAction;
import FarmioExceptions.FarmioException;
import UserCode.Conditions.BooleanConditionType;
import UserCode.Conditions.ConditionChecker;
import UserCode.Conditions.Condition;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public abstract class Task {
    protected Condition condition;
    protected Action action;

    public Task(Condition condition, Action action) {
        this.condition = condition;
        this.action = action;
    }
    /**
    public Task(JSONObject obj) throws FarmioException {
        this.condition = BooleanConditionType.valueOf((String) obj.get("condition"));
        this.action = parseJsonAction((JSONObject) obj.get("action"));
    }**/

    public boolean checkCondition() {
        return condition.check();

    }

    public abstract void execute(Ui ui);

    private Action parseJsonAction(JSONObject obj) throws FarmioException {
        Action action;
        switch ((String) obj.get("action")){
            case "plant_seed":
                action = new PlantSeedAction(obj);
                break;
            default:
                throw new FarmioException("Invalid task action.");
        }
        return action;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        //obj.put("condition", condition.name());
        obj.put("action", action.toJSON());
        return obj;
    }
}
