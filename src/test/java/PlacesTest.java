import exceptions.FarmioException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import places.WheatFarm;
import places.ChickenFarm;
import places.CowFarm;

public class PlacesTest {
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;

    public PlacesTest() {
        wheatFarm = new WheatFarm();
        chickenFarm = new ChickenFarm();
        cowFarm = new CowFarm();
    }

    @Test
    public void buySeeds() {
        wheatFarm.buySeeds();
        assertEquals(wheatFarm.getSeeds(), 1);
    }

    @Test
    public void hasSeeds() {
        wheatFarm.buySeeds();
        assert wheatFarm.hasSeeds();
    }

    @Test
    public void sellGrain() {
        wheatFarm.sell();
        assertEquals(wheatFarm.getGrain(), 0);
    }

    @Test
    public void hasGrain() {
        wheatFarm.sell();
        assert !wheatFarm.hasGrain();
    }

    @Test
    void plantSeeds() {
        int seeds = wheatFarm.getSeeds();
        wheatFarm.plantSeeds();
        assertEquals(wheatFarm.getSeedlings(), seeds);
    }

    @Test
    void growSeedlings() {
        int seedlings = wheatFarm.getSeedlings();
        wheatFarm.growSeedlings();
        assertEquals(wheatFarm.getWheat(), seedlings);
    }

    @Test
    void hasWheat() {
        wheatFarm.buySeeds();
        wheatFarm.plantSeeds();
        wheatFarm.growSeedlings();
        assert wheatFarm.hasWheat();
    }

    @Test
    void harvestWheat() {
        int wheat = wheatFarm.getWheat();
        wheatFarm.harvestWheat();
        assertEquals(wheatFarm.getGrain(), wheat);
    }

    @Test
    void buyChicken() {
        chickenFarm.buyChicken();
        assertEquals(chickenFarm.getChicken(), 1);
    }

    @Test
    void hasChicken() {
        chickenFarm.buyChicken();
        assert chickenFarm.hasChicken();
    }

    @Test
    void sellEggs() {
        chickenFarm.sell();
        assertEquals(chickenFarm.getEgg(), 0);
    }

    @Test
    void layEggs() {
        int fullChicken = chickenFarm.getFullChicken();
        chickenFarm.layEggs();
        assertEquals(chickenFarm.getEgg(), fullChicken);
    }

}
