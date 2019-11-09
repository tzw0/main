package gameassets;

import logic.usercode.tasks.Task;
import farmio.Farmio;

import java.util.ArrayList;

public class Log extends TaskList{

    protected TaskList logTasklist;
    Log(){
        logTasklist = new TaskList();
    }



    /**
     * Empties the LogList.
     * @param farmio farmio current state
     */
    public static void clearLogList(Farmio farmio){
        if (!farmio.getFarmer().getLogTaskList().isEmpty()) {
            farmio.getFarmer().getLogTaskList().deleteAll();
        }
    }

    //todo limit log printSize
    public ArrayList<String> invalidLog(){
        ArrayList<String> List = new ArrayList<String>();
        List.add("----------------INVALID-LOG-------------------");
        List.add("Please enter a number greater than 0 ");
        return List;
    }

    public ArrayList<String> emptyLog(){
        ArrayList<String> List = new ArrayList<String>();
        List.add("----------------EMPTY-LOG-------------------");
        List.add("Please populate the log");
        return List;
    }



    /**
     * Converts the logList to a readable format with index number to be printed.
     * @return String Array to be printed by the UI
     */
    @Override
    public ArrayList<String> toStringArray() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("----------------------LOG-------------------");
        for (int i = 0; i < this.size(); i++) {
            Task task = this.get(i);
            String tasktype = task.getType().toString();
            String taskCondition = task.getCondition().toString();
            String taskAction = task.getAction().toString();

            String output = "";
            output +=  ((i + 1) + ". " + "Farmer");
            if (tasktype.equals("IF")) {
               output += " checks  ";
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
    /**
     * Converts the logList to a readable format with index number to be printed.
     * @return String Array to be printed by the UI
     */
    public ArrayList<String> toStringSplitLogArray(int num) {

        ArrayList<String> splitList = new ArrayList<String>();
        int noEntries = this.size();
        int noAvailablePages = (int) Math.ceil((double)noEntries / 15);
        if(num <= 0 || num > noAvailablePages){
            splitList.addAll(invalidLog());
        } else if(noEntries == 0){
            splitList.addAll(emptyLog());
        }  else {
            splitList.add("----------------------LOG-------------------");
            int lowerBound = (num-1) * 15;
            int upperBound;
            int arbNum = num*15;
            if(noEntries < arbNum){
                upperBound = noEntries;
            } else {
                upperBound = arbNum;
            }

            for (int i = lowerBound; i < upperBound; i++) {
                Task task = this.get(i);
                String tasktype = task.getType().toString();
                String taskCondition = task.getCondition().toString();
                String taskAction = task.getAction().toString();

                String output = "";
                output +=  ((i + 1) + ". " + "Farmer");
                if (tasktype.equals("IF")) {
                    output += " checks  ";
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
                splitList.add(output);
            }
        }
        return splitList;
    }
}
