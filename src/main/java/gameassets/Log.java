package gameassets;

import farmio.exceptions.FarmioException;
import logic.usercode.tasks.Task;
import farmio.Farmio;

import java.util.ArrayList;

public class Log extends TaskList {

    protected TaskList logTasklist;

    public Log() {
        logTasklist = new TaskList();
    }



    /**
     * Empties the LogList.
     * @param farmio farmio current state
     */
    public static void clearLogList(Farmio farmio) {
        if (!farmio.getFarmer().getLogTaskList().isEmpty()) {
            farmio.getFarmer().getLogTaskList().deleteAll();
        }
    }

    /**
     * Returns a string list for invalid log.
     * @return String Array to be used by toStringSplitLogArray
     */
    public ArrayList<String> invalidLog() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("--------------------INVALID-LOG---------------------");
        list.add("Please enter a number greater than 0 ");
        return list;
    }

    /**
     * Returns a string list for empty log.
     * @return String Array to be used by toStringSplitLogArray
     */
    public ArrayList<String> emptyLog() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("----------------------EMPTY-LOG---------------------");
        list.add("                  [Log Page is empty]               ");
        return list;
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
            } else if (taskAction.equals("plantSeeds")) {
                output += " plants his seedling";
            } else if (taskAction.equals("sellGrain")) {
                output += " sells his grain";
            } else if (taskAction.equals("gotoMarket")) {
                output += " travels to the Market";
            } else if (taskAction.equals("gotoWheatFarm")) {
                output += " travels to the Wheat Farm";
            } else {
                output += "";
            }
            list.add(output);
        }

        return list;
    }
    /**
     * Converts the logList into groups of 15 and to a readable format with index number to be printed.
     * @return String Array to be printed by the UI
     */

    public ArrayList<String> toStringSplitLogArray(int num,double level) throws FarmioException {

        ArrayList<String> splitList = new ArrayList<String>();
        if (level > 1.6) {
            splitList.add("------- LOG FEATURE FOR THIS LEVEL COMING SOON -------");
        } else {
            int noEntries = this.size();
            int noAvailablePages = (int) Math.ceil((double) noEntries / 15);
            if (num <= 0 || num > noAvailablePages) {
                throw new FarmioException("Invalid LOG PAGE!");
            } else if (noEntries == 0) {
                splitList.addAll(emptyLog());
            } else {
                splitList.add("--------------------------LOG-----------------------");
                splitList.add("              [PAGE NO: " + num + " /" + noAvailablePages + "]");
                int lowerBound = (num - 1) * 15;
                int upperBound;
                int arbNum = num * 15;
                if (noEntries < arbNum) {
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
                    output += ((i + 1) + ". " + "Farmer");
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
                    } else if (taskAction.equals("plantSeeds")) {
                        output += " plants his seedling";
                    } else if (taskAction.equals("sellGrain")) {
                        output += " sells his grain";
                    } else if (taskAction.equals("gotoMarket")) {
                        output += " travels to the Market";
                    } else if (taskAction.equals("gotoWheatFarm")) {
                        output += " travels to the Wheat Farm";
                    } else {
                        output += "";
                    }
                    splitList.add(output);
                }
            }
        }

        return splitList;
    }
}
