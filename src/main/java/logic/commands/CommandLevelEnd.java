package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import gameassets.Level;
import gameassets.Farmer;
import storage.Storage;
import frontend.AsciiColours;

public class CommandLevelEnd extends Command {
    /**
     * Ends the Level and calls the next level.
     * @param farmio the game which stage is reset to LEVEL_START.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getFrontend().typeWriter("",true);
        String userInput = farmio.getFrontend().getInput();
        while (!userInput.equals("")) {
            farmio.getFrontend().simulate();
            farmio.getFrontend().showWarning("Invalid Command! Press [ENTER] to continue.");
            userInput = farmio.getFrontend().getInput();
        }
        farmio.getFrontend().simulate("LevelEnd", 0,4);
        farmio.getFrontend().show(AsciiColours.GREEN + AsciiColours.UNDERLINE +  "Level Ended" + AsciiColours.SANE);
        farmio.getFrontend().typeWriter("Farmer " + farmio.getFarmer().getName()
                + " is now ready for his next adventure! "
                + "\nPress [ENTER] to continue or Enter [skip] to skip the story", false);
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        Level level = new Level(storage.getLevel(farmer.nextLevel()),farmer.getName());
        farmio.setLevel(level);
        farmio.getFarmer().taskClear();
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}