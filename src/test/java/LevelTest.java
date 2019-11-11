import farmio.Farmio;
import gameassets.Level;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelTest {
    String object = " " +
            "\"narratives\": [\n" +
            "    \"At the market, you can buy and sell seeds in exchange for gold.\",\n" +
            "    \"The action [do buySeeds] allows you to purchase 1 unit of seed from the market.\",\n" +
            "    \"Enter [Task] [Action] to buy seeds from the market.\"\n" +
            "  ],\n" +
            "  \"feedback\": [\n" +
            "    \"You have succesfully bought seeds using the gold you have\",\n" +
            "    \"Note that a symbol '->'  will appear whenever there is a change in your assets, \"\n" +
            "  ],\n" +
            "  \"gold\": 0,\n" +
            "  \"seeds\": 1,\n" +
            "  \"seedlings\": 0,\n" +
            "  \"wheat\": 0,\n" +
            "  \"grain\": 0,\n" +
            "  \"file_path\": \"Level1.2\",\n" +
            "  \"deadline\" : 1,\n" +
            "  \"objective\": \"Buy some seeds from the market\",\n" +
            "  \"hint\": \"1.Enter [market] to check the price of a seed.\\n2.Enter [do buySeeds] to buy seeds from the market if you can afford it.\",\n" +
            "  \"modelAnswer\": \"|do buySeeds|do gotoMarket|\"" +
            "" ;

    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(object);

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
    public List<String> successfulFeedback;
    public Level level;

    public LevelTest() throws ParseException {
       Level level = new Level(json, "tester");
       narratives = level.getNarratives();
       filePath = level.getPath();
       objective = level.getObjective();
       hint = level.getHint();
       endSeeds = level.getEndSeeds();
       endSeedlings = level.getEndSeedlings();
       endWheat = level.getEndWheat();
       endGrain = level.getEndGrain();
       endGold = level.getEndGold();
       deadline = level.getDeadline();
       modelAnswer = level.getModelAnswer();
       successfulFeedback = level.getSuccessfulFeedback();

    }


    @Test
    public void convertStringtoListTest(){
        String modelAns = modelAnswer;
        List<String> expected = Arrays.asList("do buySeeds", "do gotoMarket");
        List<String> testList = level.convertStringToList(modelAnswer);
        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i),testList.get(i));
        }
    }

    @Test
    public void convertTaskListFormatTest(){
        List<String> taskList = new ArrayList<>();
        taskList.add("1.do buySeeds");
        taskList.add("2.do gotoMarket");

        List<String> testTaskList = level.convertTaskListFormat(taskList);

        List<String> compareList = new ArrayList<>();
        compareList.add("do buySeeds");
        compareList.add("do gotoMarket");

        for(int i = 0; i < compareList.size(); i++){
            assertEquals(testTaskList.get(i), compareList.get(i));
        }
    }

    @Test
    public void checkCorrectTaskTest(){

        List<String>  emptyTaskList1 = new ArrayList<>();
        List<String>  emptyTaskList2 = new ArrayList<>();

        boolean checkEmptyCondition = level.checkCorrectTasks(emptyTaskList1,emptyTaskList2);

        List<String>  incorrectTaskList1 = new ArrayList<String>(Arrays.asList("do gotoMarket", "do buySeeds"));
        List<String>  incorrectTaskList2 = new ArrayList<String>(Arrays.asList("do gotoMarket"));
        boolean checkIncorrect = level.checkCorrectTasks(incorrectTaskList1,incorrectTaskList2);

        List<String>  correctTaskList1 = new ArrayList<String>(Arrays.asList("do gotoMarket", "do buySeeds"));
        List<String>  correctTaskList2 = new ArrayList<String>(Arrays.asList("do gotoMarket", "do buySeeds"));
        boolean checkCorrect = level.checkCorrectTasks(correctTaskList1,correctTaskList2);

        assertEquals(true, checkEmptyCondition);
        assertEquals(false,checkIncorrect);
        assertEquals(true,checkCorrect);

    }
    @Test
    public void setCheckPercentageCorrectTasksTest(){


        List<String>  taskList1 = new ArrayList<String>(Arrays.asList("do gotoMarket", "do buySeeds"));
        List<String>  taskList2 = new ArrayList<String>(Arrays.asList("do gotoMarket"));
        int checkFifty = level.checkPercentageCorrectTasks(taskList1,taskList2);

        List<String>  taskList3 = new ArrayList<String>(Arrays.asList("do gotoMarket", "do buySeeds"));
        List<String>  taskList4 = new ArrayList<String>(Arrays.asList("do gotoMarket", "do buySeeds"));
        int checkHundred = level.checkPercentageCorrectTasks(taskList3,taskList4);

        assertEquals(checkHundred,100);
        assertEquals(checkFifty,50);
    }


    @Test
    public void getPermutationFeedbackTest(){
        Farmio farmio = new Farmio(false);





    }





}
