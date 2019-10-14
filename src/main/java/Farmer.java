import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import UserCode.Tasks.TaskList;
import UserInterfaces.Ui;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Farmer {
    protected int level;
    protected WheatFarm wheatFarm;
    protected ChickenFarm chickenFarm;
    protected CowFarm cowFarm;
    protected Market market;
    protected TaskList tasks;
    protected Ui ui;

    public Farmer(Ui ui) {
        this.level = 1;
        this.ui = ui;
        this.wheatFarm = new WheatFarm(); //TODO: create wheatFarm subclass
        this.chickenFarm = new ChickenFarm(); //TODO: create chickenFarm subclass
        this.cowFarm = new CowFarm(); //TODO: create cowFarm subclass
        this.tasks = new TaskList();
        this.market = new Market(100);
    }

    public Farmer(Ui ui, JSONObject jsonObject) throws FarmioException {
        this.ui = ui;
        this.level = (Integer) jsonObject.get("level");
        this.wheatFarm = new WheatFarm((JSONObject) jsonObject.get("farm_wheat"));
        this.chickenFarm = new ChickenFarm((JSONObject) jsonObject.get("farm_chicken"));
        this.cowFarm = new CowFarm((JSONObject) jsonObject.get("farm_cow"));
        this.market = new Market((Integer) jsonObject.get("money"));
        //this.tasks = new TaskList((JSONArray) jsonObject.get("task_list"));
    }

    public void startDay() {
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).execute(ui);
        }
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("level", level);
        obj.put("money", market.getMoney());
        obj.put("farm_wheat", wheatFarm.toJSON());
        obj.put("farm_chicken", chickenFarm.toJSON());
        obj.put("farm_cow", cowFarm.toJSON());
        obj.put("task_list", tasks.toJSON());
        return obj;
    }
}
