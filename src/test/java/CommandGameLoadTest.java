import farmio.Farmio;
import gameassets.Farmer;
import gameassets.Level;
import logic.commands.CommandGameLoad;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import storage.StorageDummy;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandGameLoadTest {

    private static final String FARMER_JSON = "{\"gold\":826,\"farm_chicken\":{\"chicken\":916,"
            + "\"egg\":4296,\"fullChicken\":21},\"level\":1.1,\"task_current\":-1,\"farm_wheat\":{\"seed\""
            + ":991,\"wheat\":18,\"grain\":3,\"seedling\":1},\"name\":\"FAME\",\"task_status_fail\":false,\"l"
            + "ocation\":\"WheatFarm\",\"farm_cow\":{\"milk\":888,\"cow\":437,\"fullcow\":381},\"task_list\":"
            + "[{\"condition\":{\"condition_type\":\"BOOLEAN\",\"condition_boolean_type\":\"TRUE\"},\"action\""
            + ":\"gotoMarket\",\"type\":\"DO\"}],\"day\":903}";

    private static final String LEVEL_JSON = "{\"narratives\": [\"Hi there Farmer +, you only have enough gold to"
            + " start a small farm. Why not we head over to the market to get started.\",\"Take note that you cannot"
            + " enter any game commands during the narratives. If you wish to enter any commands, press [skip].\",\""
            + "On your top right hand corner, you can see the location you are at. Currently you are at your Wheatfar"
            + "m.\",\"On the bottom left hand corner of the screen, you can see the assets you hold. Currently you ha"
            + "ve 10 gold to start your farm.\",\"On the top left corner you can see your current level which is 1.1"
            + ". This tutorial ends at level 1.4\",\"Next to that, there is a summary of the objective of the current"
            + " level. For example, for this level, the objective is to travel to the Market\",\"On the left, the sect"
            + "ion on GOALS breaks down the objective into individual goals to be accomplished. For example, for this"
            + " level, the goal is to make your locaton the market\",\"A summary list of all the commands can be acce"
            + "ssed the in-game Menu by entering [menu].\",\"If you wish to quit the game, you can either type [quit "
            + "game] or [exit game]\",\"Let's get started!\",\"The goal for this level is to change your location to t"
            + "he Market.\",\"The first command you will be using is a 'Do' command.\\nThe 'Do' command instructs farm"
            + "er + to carry out an action.\",\"The type of tasks available and the action to be executed can be seen "
            + "in the middle of the screen.\",\"From the example above, 'Do' is the type of task given and 'goToMark"
            + "et' is the action to be carried out by the farmer.\",\"Enter [do gotoMarket] to create a task that te"
            + "lls Farmer + to drive to the market.\"],\"feedback\": [\"You have succesfully travelled to the market"
            + " by using the [do] command.\",\"Note that your goal changed from white to green when it is completed "
            + "(ubuntu only)\"],\"gold\": 0,\"seeds\": 0,\"seedlings\": 0,\"wheat\": 0,\"grain\": 0,\"file_path\": \""
            + "Level1.1\",\"deadline\" : 1,\"objective\": \"Travel to the Market\",\"hint\": \"Enter [do gotoMarket] "
            + "to create a task that tells Farmer + to drive to the market.\",\"modelAnswer\": \"|do gotoMarket|\"}";

    @Test
    public void executeTest() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonFarmer = (JSONObject) parser.parse(FARMER_JSON);
            JSONObject jsonLevel = (JSONObject) parser.parse(LEVEL_JSON);
            Farmer farmer = new Farmer(jsonFarmer);
            Level level = new Level(jsonLevel, farmer.getName());
            Farmio farmio = new Farmio(false);
            StorageDummy storage = (StorageDummy) farmio.getStorage();
            storage.setFrame(new ArrayList<>());
            storage.setFarmer(jsonFarmer);
            storage.setLevel(jsonLevel);
            new CommandGameLoad().execute(farmio);
            assertEquals(FARMER_JSON, farmio.getFarmer().toJson().toString());
            assertEquals(level.getNarratives(), farmio.getLevel().getNarratives());
            assertEquals(Farmio.Stage.LEVEL_START, farmio.getStage());
        } catch (Exception e) {
            assert (false);
        }
    }
}
