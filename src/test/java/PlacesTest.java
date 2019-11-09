import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import gameassets.places.WheatFarm;
import gameassets.places.ChickenFarm;
import gameassets.places.CowFarm;

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
    public void plantSeeds() {
        int seeds = wheatFarm.getSeeds();
        wheatFarm.plantSeeds();
        assertEquals(wheatFarm.getSeedlings(), seeds);
    }

    @Test
    public void growSeedlings() {
        int seedlings = wheatFarm.getSeedlings();
        wheatFarm.growSeedlings();
        assertEquals(wheatFarm.getWheat(), seedlings);
    }

    @Test
    public void hasWheat() {
        wheatFarm.buySeeds();
        wheatFarm.plantSeeds();
        wheatFarm.growSeedlings();
        assert wheatFarm.hasWheat();
    }

    @Test
    public void harvestWheat() {
        int wheat = wheatFarm.getWheat();
        wheatFarm.harvestWheat();
        assertEquals(wheatFarm.getGrain(), wheat);
    }

    @Test
    public void buyChicken() {
        chickenFarm.buyChicken();
        assertEquals(chickenFarm.getChicken(), 1);
    }

    @Test
    public void hasChicken() {
        chickenFarm.buyChicken();
        assert chickenFarm.hasChicken();
    }
    @Test
    public void hasEgg() {
        chickenFarm.buyChicken();
        chickenFarm.layEggs();
        chickenFarm.collectEgg();
        assert chickenFarm.hasEgg();
    }

    @Test
    public void hasFullChicken() {
        chickenFarm.buyChicken();
        chickenFarm.layEggs();
        assert chickenFarm.hasFullChicken();
    }


    @Test
    public void sellEggs() {
        chickenFarm.sell();
        assertEquals(chickenFarm.getEgg(), 0);
    }

    @Test
    public void layEggs() {
        int chicken = chickenFarm.getChicken();
        chickenFarm.layEggs();
        assertEquals(chickenFarm.getFullChicken(), chicken);
    }

    @Test
    public void collectEgg() {
        int fullChicken = chickenFarm.getFullChicken();
        chickenFarm.collectEgg();
        assertEquals(chickenFarm.getEgg(), fullChicken);
        assertEquals(chickenFarm.getFullChicken(), 0);
    }

}
