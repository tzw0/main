package gameassets;

import logic.usercode.tasks.Task;
import logic.usercode.tasks.TaskList;
import farmio.Farmio;

import java.util.ArrayList;

public class Log extends TaskList{

    protected TaskList logTasklist;
    Log(){
        logTasklist = new TaskList();
    }

    //todo implement logSave via json object

    //clears the logList
    public static void clearLogList(Farmio farmio){
        if(!farmio.getFarmer().getLogTaskList().isEmpty()) { //if notempty
            farmio.getFarmer().getLogTaskList().deleteAll();//deletes everything in the log task list
        }
    }

    //todo Manipulate the Log TaskList into appropriate format
    @Override
    public ArrayList<String> toStringArray() {



        //1. check through each task
        //2. check format


        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < this.size(); i++) {
            list.add((i + 1) + ". " + this.get(i).toString());
        }
        return list;
    }






}
