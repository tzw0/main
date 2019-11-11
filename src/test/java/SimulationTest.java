import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import gameassets.Level;
import frontend.UiDummy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {
    Farmio farmio;

    /**
     * Initialises the a Farmio object with dummy ui and real storage.
     *
     * @throws FarmioFatalException if storage file of the level is not found.
     */
    public SimulationTest() throws FarmioFatalException {
        farmio = new Farmio(true);
        farmio.getFrontend().setDummyUi();
        farmio.setLevel(new Level(farmio.getStorage().getLevel(1.1), "tester"));
    }

    @Test
    void singleFullScreenSimulationTest() throws FarmioFatalException {
        farmio.getFrontend().simulate();
        assertEquals("sleepclearshow", UiDummy.uiTestString);
    }

    @Test
    void multiFullScreenSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getFrontend().simulate("Test", 0, 3, true);
        assertEquals("sleepclearshow".repeat(4), UiDummy.uiTestString);
    }

    @Test
    void singleSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getFrontend().simulate("Test",0);
        assertEquals("sleepclearshow", UiDummy.uiTestString);
    }

    @Test
    void multiFrameSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getFrontend().simulate("Test", 0, 3, true);
        assertEquals("sleepclearshow".repeat(4), UiDummy.uiTestString);
    }

    @Test
    void multiFrameReverseSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getFrontend().simulate("Test", 3, 0, true);
        assertEquals("sleepclearshow".repeat(4), UiDummy.uiTestString);
    }

    @Test
    void narrativeSimulationContinueTest() throws FarmioException, FarmioFatalException {
        UiDummy.input = "";
        double levelId = 1.1;
        while (levelId != 0) {
            UiDummy.uiTestString = "";
            farmio.setLevel(new Level(farmio.getStorage().getLevel(levelId), "tester"));
            Level level = farmio.getLevel();
            farmio.getFrontend().showNarrative();
            assert ("inputsleepclearshowtypewriter".repeat(level.getNarratives().size())
                    + "show").equals(UiDummy.uiTestString);
            levelId = farmio.getFarmer().nextLevel();
        }
    }

    @Test
    void narrativeSimulationSkipTest() throws FarmioException, FarmioFatalException {
        UiDummy.input = "skip";
        double levelId = 1.1;
        while (levelId != 0) {
            UiDummy.uiTestString = "";
            farmio.setLevel(new Level(farmio.getStorage().getLevel(levelId), "tester"));
            farmio.getFrontend().showNarrative();
            assert ("inputsleepclearshowtypewritershow").equals(UiDummy.uiTestString);
            levelId = farmio.getFarmer().nextLevel();
        }
    }
}
