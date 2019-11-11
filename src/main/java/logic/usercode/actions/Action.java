package logic.usercode.actions;

import farmio.Farmio;
import farmio.exceptions.FarmioFatalException;
import frontend.Frontend;
import storage.Storage;
import farmio.exceptions.FarmioException;
import gameassets.Farmer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.logging.Level;

public abstract class Action {

    ActionType type;

    public Action(ActionType type) {
        this.type = type;
    }

    /**
     * Executes the Action.
     *
     * @param frontend The user interface used to print messages of the action
     * @param storage which stores the assets after acton execution
     * @param farmer The farmer whose variables are displayed and changed
     * @throws FarmioException if there is an error in executing the tasklist
     * @throws FarmioFatalException if file for simulation is missing
     */
    public abstract void execute(Frontend frontend, Storage storage, Farmer farmer)
            throws FarmioException, FarmioFatalException;

    /**
     * Checks if the user input String is a valid Action.
     *
     * @param userInput substring of the user input that represents the Action
     * @return true if it is a valid Action and false otherwise
     */
    public static boolean isValidAction(String userInput) {
        for (ActionType type : ActionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the user input String to an Action object.
     *
     * @param actionName the String that is the name of the Action Object
     * @return the Action Object
     * @throws FarmioException if the name is invalid
     */
    public static Action toAction(String actionName) throws FarmioException {
        ActionType actionType = ActionType.buySeeds;
        for (ActionType a : ActionType.values()) {
            if (a.name().equalsIgnoreCase(actionName)) {
                actionType = a;
            }
        }
        switch (actionType) {
        case buySeeds:
            return new BuySeedsAction();
        case harvestWheat:
            return new HarvestWheatAction();
        case plantSeeds:
            return new PlantSeedsAction();
        case sellGrain:
            return new SellGrainAction();
        case gotoMarket:
            return new GotoMarketAction();
        case gotoWheatFarm:
            return new GotoFarmAction();
        default:
            throw new FarmioException("Error Creating Action!");
        }
    }

    public String toString() {
        return type.name();
    }

    /**
     * Checks if Action criteria is met before execution.
     *
     * @param frontend The user interface used to print messages of the action
     * @param farmer The farmer whose variables are displayed and changed
     * @param criteriaFeedbackList The list of criterias and their respective feedback messages
     * @throws FarmioException if there is an error in executing the tasklist
     * @throws FarmioFatalException if file for simulation is missing

     */
    protected void checkActionCriteria(Frontend frontend, Farmer farmer,
                                       ArrayList<Pair<Boolean, String>> criteriaFeedbackList)
            throws FarmioException, FarmioFatalException {
        boolean hasError = false;
        frontend.sleep(2000);
        for (Pair<Boolean, String> criteriaFeedback: criteriaFeedbackList) {
            if (criteriaFeedback.getKey()) {
                if (!hasError) {
                    farmer.setTaskFailed();
                    frontend.simulate("ErrorInExecution", 1, 9);
                    hasError = true;
                }
                frontend.show(criteriaFeedback.getValue());
            }
        }
        if (hasError) {
            Farmio.LOGGER.log(Level.INFO, this.toString() + " executed with an error");
            frontend.typeWriter("",true);
            String userInput = frontend.getInput();
            while (!userInput.equals("")) {
                frontend.simulate();
                frontend.showWarning("Invalid Command! Press [ENTER] to continue.");
                userInput = frontend.getInput();
            }
            throw new FarmioException("Task Error!");
        }
        Farmio.LOGGER.log(Level.INFO, this.toString() + " executed");
    }
}
