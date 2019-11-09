package gameassets;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.GameConsole;
import gameassets.places.ChickenFarm;
import gameassets.places.CowFarm;
import gameassets.places.WheatFarm;
import logic.usercode.tasks.Task;
import logic.usercode.tasks.TaskList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Farmer {

    private static final String JSON_KEY_GOLD = "gold";
    private static final String JSON_KEY_LEVEL = "level";
    private static final String JSON_KEY_DAY = "day";
    private static final String JSON_KEY_LOCATION = "location";
    private static final String JSON_KEY_FARM_WHEAT = "farm_wheat";
    private static final String JSON_KEY_FARM_CHICKEN = "farm_chicken";
    private static final String JSON_KEY_FARM_COW = "farm_cow";
    private static final String JSON_KEY_TASK_LIST = "task_list";
    private static final String JSON_KEY_TASK_CURRENT = "task_current";
    private static final String JSON_KEY_TASK_STATUS_FAIL = "task_status_fail";
    private static final String JSON_KEY_NAME = "name";
    protected Log logTaskList;

    private int gold;
    private double level;
    private int day;
    private String name;
    private String location;
    protected WheatFarm wheatFarm;
    protected ChickenFarm chickenFarm;
    protected CowFarm cowFarm;
    protected TaskList tasks;
    private int currentTask;
    private boolean hasfailedCurrentTask;
    private ArrayList<Double> levelList = new ArrayList<Double>(Arrays.asList(1.1,1.2,1.3,1.4,1.5,1.6,2.1));


    /**
     * Constructor for Farmer to intialize farmer object.
     */
    public Farmer() {
        this.gold = 10;
        this.level = 1.1;
        this.day = 1;
        this.location = "WheatFarm";
        this.wheatFarm = new WheatFarm();
        this.chickenFarm = new ChickenFarm();
        this.cowFarm = new CowFarm();
        this.tasks = new TaskList();
        this.currentTask = -1;
        this.hasfailedCurrentTask = false;
        this.name = "name";
        this.logTaskList = new Log();
    }

    /**
     * Checks whether the name that was loaded from the save file is a valid name.
     * @param loadName as the name that is loaded from the save file.
     * @throws FarmioException if loadName does not meet the conditions of the name.
     */
    private void isValidName(String loadName) throws FarmioException {
        boolean hasError = false;
        if (loadName.equals("MENU") || !(loadName.length() <= 15 && loadName.length() > 0
                && (loadName.matches("[a-zA-Z0-9]+") || loadName.contains("_")))) {
            hasError = true;
        }

        if (hasError) {
            throw new FarmioException("Invalid Name!");
        }
    }

    private void isValidTaskList(TaskList tasks) throws FarmioException {
        if (tasks.size() > GameConsole.FRAME_SECTION_HEIGHT) {
            throw new FarmioException("Too many tasks!");
        }
    }

    /**
     * Adds the user's name.
     * @param username as the name the user inputs.
     */
    public void inputName(String username) {
        name = username;
    }

    /**
     * Gets user's name.
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the amount of gold the farmer has.
     * @return the amount of gold.
     */
    public int getGold() {
        return gold;
    }

    public boolean hasGold() {
        return gold > 0;
    }

    /**
    * Gets user level.
    * @return the user level.
    */
    public double getLevel() {
        return level;
    }

    /**
     * Gets the day the farmer is at.
     * @return the day.
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets user location.
     * @return the user location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets user input.
     * @param newLocation as the new location of the user.
     */
    public void changeLocation(String newLocation) {
        location = newLocation;
    }

    /**
     * Gets user assets based on level.
     */
    public Map<String, Integer> getAssets() {
        Map<String, Integer> assets = new HashMap<>();

        if (level >= 1.4) {
            assets.put("Wheat", wheatFarm.getWheat());
            assets.put("Grain", wheatFarm.getGrain());
        }
        if (level >= 1.3) {
            assets.put("Seedlings", wheatFarm.getSeedlings());
        }
        if (level >= 1.2) {
            assets.put("Seeds", wheatFarm.getSeeds());
        }
        if (level >= 1.1) {
            assets.put("Gold", gold);
        }
        return assets;
    }

    /**
     * Gets user wheatfarm.
     * @return the user wheatfarm.
     */
    public WheatFarm getWheatFarm() {
        return  wheatFarm;
    }

    /**
     * Gets user chickenfarm.
     * @return the user chickenfarm.
     */
    public ChickenFarm getChickenFarm() {
        return chickenFarm;
    }

    /**
     * Gets user cowfarm.
     * @return the user cowfarm.
     */
    public CowFarm getCowFarm() {
        return cowFarm;
    }

    /**
     * Gets user tasks to be executed.
     * @return the user tasks.
     */
    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Gets the log list
     * @return
     */
    public Log getLogTaskList(){
        return  logTaskList;
    }

    public void addTask(Task task) throws FarmioException {
        tasks.addTask(task);
    }

    public void insertTask(int taskID, Task task) throws FarmioException {
        tasks.insertTask(taskID, task);
    }

    public void editTask(int taskID, Task task) throws FarmioException {
        tasks.editTask(taskID,task);
    }

    public ArrayList<String> toStringArray() {
        return tasks.toStringArray();
    }

    public String deleteTask(int taskID) throws FarmioException {
        return tasks.deleteTask(taskID);
    }

    public void deleteAll() {
        tasks.deleteAll();
    }


    public void taskClear() {
        tasks.clear();
    }

    public int taskSize() {
        return tasks.size();
    }
    /**
     * Checks if curent task has failed and resets current task.
     * @return true if current task has failed and false otherwise.
     */
    public boolean isHasfailedCurrentTask() {
        if (hasfailedCurrentTask) {
            return true;
        }
        return false;
    }

    /**
     * Reverts task list execution failure.
     */
    public void resetTaskFailed() {
        hasfailedCurrentTask = false;
        currentTask = -1;
    }

    /**
     * Sets task list execution as failed.
     */
    public void setTaskFailed() {
        hasfailedCurrentTask = true;
    }

    /**
     * Decrements gold after buying an item.
     * @param cost as the buying price of the item.
     */
    public void spendGold(int cost) {
        gold -= cost;
    }

    /**
     * Increments gold after selling an item.
     * @param profit as the selling price of the item.
     */
    public void earnGold(int profit) {
        gold += profit;
    }

    /**
     * Gets the index of the current task in execution.
     * @return the index of the current task.
     */
    public int getCurrentTask() {
        return this.currentTask;
    }

    /**
     * Increases the level.
     * @return the next level number. 0 if current level is not registered or game has ended
     */
    public double nextLevel() {
        if (level < levelList.get(levelList.size() - 1)) {
            level = levelList.get(levelList.indexOf(level) + 1);
            return level;
        }
        return 0;
    }

    /**
     * Takes care of TaskList execution and handles task failure.
     * @param farmio The game where the day should be started.
     * @throws FarmioFatalException if file from action's simulation cannot be found.
     */
    public void startDay(Farmio farmio) throws FarmioFatalException {
        try {
            for (int i = 0; i < tasks.size(); i++) {
                this.currentTask = i;

                //todo add the log to the logList
                logTaskList.add(tasks.get(i)); //adds tasks to logTaskList

                tasks.get(i).execute(farmio);
            }
        } catch (FarmioException e) {
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        } finally {
            this.currentTask = -1;
        }
    }

    /**
     * Proceeds to the next day.
     * Allows seeds to grow into wheat and increment day number.
     */
    public void nextDay() {
        wheatFarm.growSeedlings();
        day += 1;
    }

    public boolean hasSeeds() {
        return wheatFarm.hasSeeds();
    }

    public boolean hasWheat() {
        return wheatFarm.hasWheat();
    }

    public boolean hasGrain() {
        return wheatFarm.hasGrain();
    }

    public int getSeeds() {
        return wheatFarm.getSeeds();
    }

    public int getSeedlings() {
        return wheatFarm.getSeedlings();
    }

    public int getWheat() {
        return wheatFarm.getWheat();
    }

    public int getGrain() {
        return wheatFarm.getGrain();
    }

    public void buySeeds() {
        wheatFarm.buySeeds();
    }

    public void harvestWheat() {
        wheatFarm.harvestWheat();
    }

    public void plantSeeds() {
        wheatFarm.plantSeeds();
    }

    public int sellGrain() {
        return wheatFarm.sell();
    }

    /**
     * Change farmer variables with the data in argument JSONObject.
     * @param jsonObject data representation of farmer.
     * @return
     * @throws FarmioException
     */
    public Farmer setJson(JSONObject jsonObject) throws FarmioException {
        try {
            this.level = (Double) jsonObject.get(JSON_KEY_LEVEL);
            this.gold = (int) (long) jsonObject.get(JSON_KEY_GOLD);
            this.day = (int) (long) jsonObject.get(JSON_KEY_DAY);
            this.location = (String) jsonObject.get(JSON_KEY_LOCATION);
            this.wheatFarm = new WheatFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_WHEAT));
            this.chickenFarm = new ChickenFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_CHICKEN));
            this.cowFarm = new CowFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_COW));
            this.tasks = new TaskList((JSONArray) jsonObject.get(JSON_KEY_TASK_LIST));
            this.currentTask = -1;
            this.hasfailedCurrentTask = false;
            String savedName = (String) jsonObject.get(JSON_KEY_NAME);
            String loadName = savedName.toUpperCase();
            isValidName(loadName);
            isValidTaskList(this.tasks);
            this.name = loadName;
        } catch (Exception e) {
            throw new FarmioException("Game save corrupted!");
        }
        return this;
    }

    /**
     * Generates a json representation of farmer to be stored into a file.
     * @return JSONOBject with farmer data.
     */
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put(JSON_KEY_LEVEL, level);
        obj.put(JSON_KEY_GOLD, gold);

        obj.put(JSON_KEY_DAY, day);
        obj.put(JSON_KEY_LOCATION, location);
        obj.put(JSON_KEY_FARM_WHEAT, wheatFarm.toJson());
        obj.put(JSON_KEY_FARM_CHICKEN, chickenFarm.toJson());
        obj.put(JSON_KEY_FARM_COW, cowFarm.toJson());
        obj.put(JSON_KEY_TASK_LIST, tasks.toJson());
        obj.put(JSON_KEY_TASK_CURRENT, currentTask);
        obj.put(JSON_KEY_TASK_STATUS_FAIL, hasfailedCurrentTask);
        obj.put(JSON_KEY_NAME, name);
        return obj;
    }

    /**
     * Updates TaskList in argument json data to match TaskList in farmer.
     * @param object Json data to be updated.
     * @return Updated json data.
     */
    public JSONObject updateJson(JSONObject object) {
        object.replace(JSON_KEY_TASK_LIST, tasks.toJson());
        return object;
    }
}