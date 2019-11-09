package gameassets;

import logic.usercode.tasks.Task;
import farmio.Farmio;

import java.util.ArrayList;

public class Log extends TaskList{

    protected TaskList logTasklist;
    Log(){
        logTasklist = new TaskList();
    }

    //todo implement logSave via json object

    /**
     * Empties the LogList.
     * @param farmio farmio current state
     */
    public static void clearLogList(Farmio farmio){
        if (!farmio.getFarmer().getLogTaskList().isEmpty()) {
           farmio.getFarmer().getLogTaskList().deleteAll();
        }
    }

    //todo Manipulate the Log TaskList into appropriate format

    /**
     * Converts the logList to a readable format with index number to be printed.
     * @return String Array to be printed by the UI
     */
    @Override
    public ArrayList<String> toStringArray() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < this.size(); i++) {
            Task task = this.get(i);
            String tasktype = task.getType().toString();
            String taskCondition = task.getCondition().toString();
            String taskAction = task.getAction().toString();

            String output = "";
            //output += "Task Type: " + tasktype  + " Task Condition:" + taskCondition + " Task Action :" + taskAction;
            output +=  ((i + 1) + ". " + "Farmer");
            if (tasktype.equals("IF")) {
               output += " checks  " ;
               if (taskCondition.equals("hasSeeds")) {
                   output += "if he has seeds in his assets";
                } else if (taskCondition.equals("hasWheat")) {
                   output += "if there is any wheat which can be harvested";
                } else if (taskCondition.equals("hasGrain")) {
                   output += "if there's any grain which he can sell";
                } else {
                   output += "if " + taskCondition;
                }
                output += " then he";
            }
            if (taskAction.equals("buySeeds")) {
               output += " buys some seeds";
            } else if (taskAction.equals("harvestWheat")) {
               output += " harvest the wheat";
            }
            else if (taskAction.equals("plantSeeds")) {
               output += " plants his seedling";
            }
            else if (taskAction.equals("sellGrain")) {
               output += " sells his grain";
            }
            else if (taskAction.equals("gotoMarket")) {
               output += " travels to the Market";
            }
            else if (taskAction.equals("gotoWheatFarm")) {
               output += " travels to the Wheat Farm";
            }
            else {
               output += "";
            }
            list.add(output);
        }

        return list;
    }

}
