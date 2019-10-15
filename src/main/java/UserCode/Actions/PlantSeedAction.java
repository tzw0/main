package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import Simulations.Simulate;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public class PlantSeedAction extends Action {

    public PlantSeedAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market) {
        super(wheatFarm, chickenFarm, cowFarm, market);
    }

    public PlantSeedAction(JSONObject obj){
        super(obj);
    }

    @Override
    public void execute(Ui ui) {
        try {
            wheatFarm.plantSeeds();
            new Simulate(ui, "PlantSeedSimulation", 10).simulate();
        } catch (Exception e){
            e.getMessage();
        }
    }

    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("action", "plant_seed");
        return obj;
    }
}
