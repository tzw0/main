import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Frontend;
import gameassets.Level;
import gameassets.Farmer;
import org.junit.jupiter.api.Test;
import logic.usercode.actions.GotoFarmAction;
import logic.usercode.actions.GotoMarketAction;
import logic.usercode.actions.BuySeedsAction;
import logic.usercode.actions.PlantSeedsAction;
import logic.usercode.actions.HarvestWheatAction;
import logic.usercode.actions.SellGrainAction;
import logic.usercode.actions.Action;

import storage.Storage;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ActionTest {
    private static final ArrayList<String> LIST_OF_VALID_ACTIONS =
            new ArrayList<>(Arrays.asList("gotomarket", "gotowheatfarm", "buyseeds", "plantseeds",
                    "harvestwheat", "sellgrain"));
    private Action gotoMarketAction;
    private Action buySeedsAction;
    private Action gotoWheatFarmAction;
    private Action plantSeedsAction;
    private Action harvestWheatAction;
    private Action sellGrainAction;

    /**
     * Initialises the actions for testing.
     */
    public ActionTest() {
        gotoMarketAction = new GotoMarketAction();
        buySeedsAction = new BuySeedsAction();
        gotoWheatFarmAction = new GotoFarmAction();
        plantSeedsAction = new PlantSeedsAction();
        harvestWheatAction = new HarvestWheatAction();
        sellGrainAction = new SellGrainAction();
    }

    @Test
    void isValidActionPositiveTest() {
        for (String validAction: LIST_OF_VALID_ACTIONS) {
            assert Action.isValidAction(validAction);
        }
    }

    @Test
    void isValidActionNegativeTest() {
        assert !Action.isValidAction("");
        assert !Action.isValidAction("go to market");
        assert !Action.isValidAction("goto wheatfarm");
        assert !Action.isValidAction("buyseed");
        assert !Action.isValidAction(" plantseed");
        assert !Action.isValidAction("PLANTSEED");
    }

    @Test
    void toActionPositiveTest() throws FarmioException {
        assert Action.toAction("gotomarket") instanceof GotoMarketAction;
        assert Action.toAction("gotowheatfarm") instanceof GotoFarmAction;
        assert Action.toAction("buyseeds") instanceof BuySeedsAction;
        assert Action.toAction("plantseeds") instanceof PlantSeedsAction;
        assert Action.toAction("harvestwheat") instanceof HarvestWheatAction;
        assert Action.toAction("sellgrain") instanceof SellGrainAction;
    }


    @Test
    void toActionNegativeTest() throws FarmioException {
        assertFalse(Action.toAction("") instanceof GotoMarketAction);
        for (String validAction: LIST_OF_VALID_ACTIONS) {
            if (!validAction.equals("gotomarket")) {
                assertFalse(Action.toAction(validAction) instanceof GotoMarketAction);
            }
        }
        for (String validAction: LIST_OF_VALID_ACTIONS) {
            if (!validAction.equals("gotowheatfarm")) {
                assertFalse(Action.toAction(validAction) instanceof GotoFarmAction);
            }
        }
        for (String validAction: LIST_OF_VALID_ACTIONS) {
            if (!validAction.equals("buyseeds")) {
                assertFalse(Action.toAction(validAction) instanceof BuySeedsAction);
            }
        }
        for (String validAction: LIST_OF_VALID_ACTIONS) {
            if (!validAction.equals("sellgrain")) {
                assertFalse(Action.toAction(validAction) instanceof SellGrainAction);
            }
        }
        for (String validAction: LIST_OF_VALID_ACTIONS) {
            if (!validAction.equals("plantseeds")) {
                assertFalse(Action.toAction(validAction) instanceof PlantSeedsAction);
            }
        }
        for (String validAction: LIST_OF_VALID_ACTIONS) {
            if (!validAction.equals("harvestwheat")) {
                assertFalse(Action.toAction(validAction) instanceof HarvestWheatAction);
            }
        }
    }

    @Test
    void actionToStringTest() {
        assert gotoMarketAction.toString().equals("gotoMarket");
        assert gotoWheatFarmAction.toString().equals("gotoWheatFarm");
        assert buySeedsAction.toString().equals("buySeeds");
        assert plantSeedsAction.toString().equals("plantSeeds");
        assert harvestWheatAction.toString().equals("harvestWheat");
        assert sellGrainAction.toString().equals("sellGrain");
    }

    @Test
    void actionSequenceOfGameTest() throws FarmioException, FarmioFatalException {
        Farmio farmioRealStorage = new Farmio(true);
        farmioRealStorage.getFrontend().setDummyUi();
        Frontend frontendDummy = farmioRealStorage.getFrontend();
        Storage storageActual = farmioRealStorage.getStorage();
        Farmer farmer = farmioRealStorage.getFarmer();
        farmioRealStorage.setLevel(new Level(storageActual.getLevel(1.1), "tester"));
        gotoMarketAction.execute(frontendDummy, storageActual, farmer);
        buySeedsAction.execute(frontendDummy, storageActual, farmer);
        gotoWheatFarmAction.execute(frontendDummy, storageActual, farmer);
        plantSeedsAction.execute(frontendDummy, storageActual, farmer);
        farmer.nextDay();
        harvestWheatAction.execute(frontendDummy, storageActual, farmer);
        gotoMarketAction.execute(frontendDummy, storageActual, farmer);
        sellGrainAction.execute(frontendDummy, storageActual, farmer);
    }
}