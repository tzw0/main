package gameassets;

import farmio.Farmio;
import gameassets.Farmer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Level {
    private ArrayList<String> narratives;
    private String filePath;
    private String objective;
    private String hint;
    private int endSeeds;
    private int endSeedlings;
    private int endWheat;
    private int endGrain;
    private int endGold;
    private int deadline;
    public String modelAnswer;
    public ArrayList<String> successfulFeedback;

    private boolean detailedFeedbackProvided = true;
    private boolean incompleteObjectives = false;

    /**
     * Intitalises variables based off values obtain form the JSON File.
     * @param object JSON Object to be parsed.
     * @param name player's name
     */
    public Level(JSONObject object, String name) {
        JSONArray array = (JSONArray) object.get("narratives");
        narratives = new ArrayList<>();
        for (Object i : array) {
            String line = (String) i;
            line = line.replace("+", name);
            narratives.add(line);
        }

        JSONArray feedbackarray = (JSONArray) object.get("feedback");
        successfulFeedback = new ArrayList<>();
        for (Object i : feedbackarray) {
            successfulFeedback.add((String) i);
        }

        filePath = (String) object.get("file_path");
        endGold = Math.toIntExact((Long) object.get("gold"));
        endSeeds = Math.toIntExact((Long) object.get("seeds"));
        endSeedlings = Math.toIntExact((Long) object.get("seedlings"));
        endWheat = Math.toIntExact((Long) object.get("wheat"));
        endGrain = Math.toIntExact((Long) object.get("grain"));
        deadline = Math.toIntExact((Long) object.get("deadline"));
        objective = (String) object.get("objective");
        hint = ((String) object.get("hint")).replace("+", name);
        modelAnswer = (String) object.get("modelAnswer");

    }

    /**
     * Get the narrative of the level .
     * @return the list of narrative
     */
    public ArrayList<String> getNarratives() {
        return narratives;
    }

    /**
     * Get hint for completing the level.
     * @return the hint
     */
    public String getHint() {
        return hint;
    }

    /**
     * Get path for simulation of the level's narrative.
     * @return the file path
     */
    public String getPath() {
        return filePath;
    }

    public enum ObjectiveResult {
        NOT_DONE,
        DONE,
        FAILED,
        INVALID
    }

    public boolean checkDeadlineExceeded(int currentDay) {
        return deadline < currentDay;
    }

    private boolean allDone(Farmer farmer) {
        if (farmer.getLevel() == 1.1) {
            return farmer.getLocation().equals("Market");
        }
        int seeds = farmer.wheatFarm.getSeeds();
        int seedlings = farmer.wheatFarm.getSeedlings();
        int wheat = farmer.wheatFarm.getWheat();
        int grain = farmer.wheatFarm.getGrain();
        int gold = farmer.getGold();
        return (seeds >= endSeeds) && (wheat >= endWheat) && (grain >= endGrain) && (seedlings >= endSeedlings)
                && (gold >= endGold);
    }

    /**
     * Checks on the state of the level.
     * @param farmio Farmio Object
     * @return enum current state of the level
     */
    public ObjectiveResult checkAnswer(Farmio farmio) {
        if (farmio.getFarmer().isHasfailedCurrentTask()) {
            return ObjectiveResult.INVALID;
        }
        Farmer farmer = farmio.getFarmer();
        ObjectiveResult levelState;
        int day = farmer.getDay();
        if (checkDeadlineExceeded(day)) {
            levelState = ObjectiveResult.FAILED;
        } else {
            if (allDone(farmer)) {
                levelState = ObjectiveResult.DONE;
            } else if (checkDeadlineExceeded(day + 1)) {
                levelState = ObjectiveResult.FAILED;
            } else {
                levelState = ObjectiveResult.NOT_DONE;
            }
        }
        return levelState;
    }

    /**
     * Checks for incomplete objectives and returns feedback on objectives.
     * @param farmer Farmer object
     * @return Feedback on incomplete objectives
     */
    private List<String> checkIncompleteObjectives(Farmer farmer) {
        List<String> output = new ArrayList<String>();
        int seeds = farmer.wheatFarm.getSeeds();
        int wheat = farmer.wheatFarm.getWheat();
        int grain = farmer.wheatFarm.getGrain();
        int gold = farmer.getGold();
        double levelNumber = farmer.getLevel();
        if (levelNumber == 1.1) {
            return output;
        }

        if (levelNumber == 1.2) {
            if (seeds != endSeeds) {

                int balancedWheatSeed = endSeeds - seeds;

                output.add(" Seeds left :" + balancedWheatSeed);
            } else {
                output.add(" Seeds Completed");
            }
        }

        if (levelNumber == 1.5) {
            if (grain != endGrain) {
                int balancedWheatRipe =   grain - endGrain;
                output.add(" | Grain left :" + balancedWheatRipe);
            } else {
                output.add(" | Grain completed");
            }
        }

        if (levelNumber == 1.6 || levelNumber == 1.5) {
            if (gold != endGold) {

                int balancedGold = endGold - gold;
                output.add(" Gold required :" + balancedGold);

            } else {
                output.add(" gold Completed");
            }
        }


        if (levelNumber == 1.4) {
            if (grain != endGrain) {
                int balancedWheatRipe = endGrain - grain;
                output.add(" | Grain left :" + balancedWheatRipe);
            } else {
                output.add(" | Grain completed");
            }
        }

        return output;
    }

    /**
     * Splits string by | to List of Strings.
     * @param modelAnswer String to be split
     * @return List of Strings
     */
    public List<String> convertStringToList(String modelAnswer) {
        String[] taskItems = modelAnswer.split("\\|");
        List<String> modelTaskList = new ArrayList<>();
        for (int i = 1; i < taskItems.length; i++) {
            modelTaskList.add(taskItems[i]);
        }
        return  modelTaskList;
    }

    /**
     * Converts numbered format for tasks to a  standard format Strings.
     * @param taskList  List of strings to be converted
     * @return newly formatted List of Strings
     */
    public List<String> convertTaskListFormat(List<String> taskList) {

        List<String> splitTaskList = new ArrayList<String>();
        for (String taskListItems: taskList) {
            String removedNumbering = taskListItems.substring(taskListItems.indexOf(".") + 1);
            splitTaskList.add(removedNumbering.trim());
        }
        return splitTaskList;
    }

    /**
     * Check whether the user has all the necessary task.
     * @param modelList model answer list
     * @param userList user task list
     * @return whether the tasks are equal
     */
    public boolean checkCorrectTasks(List<String> modelList, List<String> userList) {
        if (modelList == null && userList == null) {
            return true;
        }

        if ((modelList == null && userList != null) || modelList != null && userList == null
                || modelList.size() != userList.size()) {
            return false;
        }

        modelList = new ArrayList<String>(modelList);
        userList = new ArrayList<String>(userList);

        Collections.sort(userList);
        Collections.sort(modelList);

        return userList.equals(modelList);
    }

    /**
     * Checks the percentage of tasks that are correct.
     * @param modelList model task List
     * @param userList user task list
     * @return percentage of correct tasks
     */
    public int checkPercentageCorrectTasks(List<String> modelList, List<String> userList) {
        if (modelList == null && userList == null) {
            return 0;
        }

        modelList = new ArrayList<String>(modelList);
        userList = new ArrayList<String>(userList);

        Collections.sort(userList);
        Collections.sort(modelList);

        int ctr = 0;
        for (int i = 0; i < modelList.size(); i++) {
            String userTask = userList.get(i);
            String modeltask = modelList.get(i);
            if (userTask.equals(modeltask)) {
                ctr += 1;
            }
        }
        double percentage = (ctr * 100.0f) / modelList.size();
        return (int) percentage;
    }

    /**
     * Compares the lists precision.
     * @param modelList List of model answers
     * @param userList List of the users answers
     * @return precision of the list
     */
    public int compareLists(List<String> modelList, List<String> userList) {
        double sameTaskType = 0;
        double sameActionType = 0;
        if (checkCorrectTasks(modelList,userList)) {
            int percentageCorrect = checkPercentageCorrectTasks(modelList, userList);
            return percentageCorrect;
        } else {
            for (int i = 0; i < modelList.size(); i++) {
                String[] modelTaskString = modelList.get(i).split(" ", 2);
                String[] userTaskString = userList.get(i).split(" ", 2);
                String modelTask = modelTaskString[0];
                String userTask = userTaskString[0];
                String modelAction = modelTaskString[1];
                String userAction = userTaskString[1];
                if (modelTask.equals(userTask)) {
                    sameTaskType += 1;
                }
                if (modelAction.equals(userAction)) {
                    sameActionType += 1;
                }

            }

            double probTask = (sameTaskType * 100.0f) / modelList.size();
            double probAction = (sameActionType * 100.0f) / modelList.size();
            double precision = (probAction * probTask) / 100;
            return (int) precision;

        }

    }

    /**
     * Returns the feedback based off the different permutations of tasks when the level failes.
     * @param farmio farmio current state
     * @param levelNumber the level the game is currently running
     * @return feedback on failed tasks
     */
    public List<String> getPermutationFeedback(Farmio farmio,double levelNumber) {
        List<String> output = new ArrayList<String>();
        List<String> userTaskList = farmio.getFarmer().tasks.toStringArray();
        List<String> modelTaskList = convertStringToList(modelAnswer);
        List<String> modifieduserTaskList = convertTaskListFormat(userTaskList);


        if (levelNumber >= 1.2) {
            int sizeDifference = compareSizeDifference(modelTaskList, modifieduserTaskList);
            output.add("Correct number of Tasks: " + sizeDifference + "%");

            if (sizeDifference == 100) {
                if (checkCorrectTasks(modelTaskList,modifieduserTaskList)) {
                    output.add("You have the correct number and types of tasks required, "
                            + "Please check the order of your tasks");
                }
                int correctTaskPercentage  = compareLists(modelTaskList, modifieduserTaskList);
                output.add(" % of correct Tasks: " + correctTaskPercentage + "%");
            }
        }

        return output;
    }

    /**
     * Returns the accuracy in the number of the task to the user.
     * @param modelAnswer model answer list
     * @param userAnswer  user answer list
     * @return sizeDifference
     */
    public int compareSizeDifference(List<String> modelAnswer, List<String> userAnswer) {
        int modelAnswerSize = modelAnswer.size();
        int userAnswerSize = userAnswer.size();
        if (modelAnswerSize == userAnswerSize) {
            return 100;
        }    else if (modelAnswerSize > userAnswerSize) {
            return (int)((userAnswerSize * 100.0f) / modelAnswerSize);
        }    else {
            return (int)((modelAnswerSize * 100.0f) / userAnswerSize);
        }
    }

    /**
     * Feedback on failed objectives.
     * @param farmio farmio
     * @return feedback
     */
    public List<String> getDetailedFeedback(Farmio farmio) {
        List<String> output = new ArrayList<String>();
        double levelNumber = farmio.getFarmer().getLevel();
        output.add("The objective of this level was to " + objective);
        output.add("Unfortunately you were unable to complete within the allocated time of " + deadline + " days");

        output.add("Your actions :");
        output.add(farmio.getFarmer().tasks.toString());

        output.addAll(getPermutationFeedback(farmio, levelNumber));

        return output;
    }

    /**
     *  Returns feedback for the succesful completion of a level.
     * @return List of succesfull feedbacks
     */
    public List<String> getSuccessfulFeedback() {
        List<String> output = new ArrayList<String>();
        for (String x: successfulFeedback) {
            output.add(x);
        }
        return output;
    }

    /**
     * Returns different feedback based on the levelState.
     * @param farmio farmio object
     * @param currentLevelState current state of the level
     * @return feedback
     */

    public List<String> getFeedback(Farmio farmio, ObjectiveResult currentLevelState) {

        Farmer farmer = farmio.getFarmer();
        List<String> output = new ArrayList<String>();
        if (currentLevelState == ObjectiveResult.DONE) {
            output.addAll(getSuccessfulFeedback());
            output.add("well done you have completed the level - all tasks has been completed succesfully");
            return output;
        } else if (currentLevelState == ObjectiveResult.NOT_DONE) {
            String feedback = "tasks have yet to be completed";
            output.add(feedback);
            if (incompleteObjectives) {
                output.add("detailed feedback : -- \n");
                output.addAll(checkIncompleteObjectives(farmer));
            }
            output.add("Press [ENTER] to continue the game or enter [reset] to restart the level");
            return output;
        } else if (currentLevelState == ObjectiveResult.FAILED) {
            String feedback = "Oh no! The objectives were not met by the deadline! Level failed ! \n";
            output.add(feedback);
            if (detailedFeedbackProvided) {
                output.addAll(getDetailedFeedback(farmio));
            }
            return output;
        } else if (currentLevelState == ObjectiveResult.INVALID) {
            output.add("Oh no! There has been an error during code execution!");
            return output;
        }
        return output;
    }

    /**
     * Get the list of goals to be completed.
     * @return the list of goals
     */
    public Map<String,Integer> getGoals() {
        Map<String,Integer> goals = new HashMap<String,Integer>();
        goals.put("Gold", endGold);
        goals.put("Seeds", endSeeds);
        goals.put("Seedlings", endSeedlings);
        goals.put("Wheat", endWheat);
        goals.put("Grain", endGrain);
        return goals;
    }

    /**
     * Get the main objective of the level.
     * @return the objective of the level
     */
    public String getObjective() {
        return objective;
    }

    /**
     * Get the number of seeds required to complete the level.
     * @return number of seeds required
     */
    public int getEndSeeds() {
        return endSeeds;
    }


    /**
     * Get the number of seedslings required to complete the level.
     * @return number of seedlings required
     */
    public int getEndSeedlings() {
        return endSeedlings;
    }

    /**
     * Get the number of wheat required to complete the level.
     * @return number of wheat required
     */
    public int getEndWheat() {
        return endWheat;
    }

    /**
     * Get the number of grain required to complete the level.
     * @return number of grain required
     */
    public int getEndGrain() {
        return endGrain;
    }

    /**
     * Get the number of gold required to complete the level.
     * @return number of gold required
     */
    public int getEndGold() {
        return endGold;
    }

    /**
     * Get the deadline for the level.
     * @return deadline for the level
     */
    public int getDeadline() {
        return deadline;
    }

    /**
     * Get model answer for the level.
     * @return model answer for the level
     */
    public String getModelAnswer() {
        return modelAnswer;
    }




}