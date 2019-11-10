package logic.commands;

import farmio.exceptions.FarmioFatalException;
import gameassets.Farmer;
import farmio.Farmio;
import storage.Storage;
import gameassets.Level;
import frontend.Frontend;
import farmio.exceptions.FarmioException;

public class CommandGameLoad extends Command {
    /**
     * Tries to Load the game and creates a new game if unsuccessful.
     * @param farmio the game to be resumed from load state.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Frontend frontend = farmio.getFrontend();
        Storage storage = farmio.getStorage();
        try {
            Farmer farmer = new Farmer().setJson(storage.loadFarmer());
            farmio.setFarmer(farmer);
            Level level = new Level(storage.getLevel(farmer.getLevel()), farmer.getName());
            farmio.setLevel(level);
            farmio.getFrontend().simulate("GameLoad", 0, 8, true);
            farmio.getFrontend().simulate("GameLoad", 9);
            frontend.typeWriter("Load Game Successful!, press [ENTER] to continue or enter [skip] to skip the story..",
                    false);
        } catch (FarmioException e) {
            if (farmio.getStage() == Farmio.Stage.MENU_START || farmio.getStage() == Farmio.Stage.WELCOME) {
                farmio.getFrontend().simulate("GameNew", 0, true);
                frontend.showWarning(e.getMessage() + " Starting a new game.");
                frontend.typeWriter("New Game Created!", false);
                frontend.typeWriter("Enter your name:", false);
                farmio.setStage(Farmio.Stage.NAME_ADD);
                return;
            } else {
                farmio.getFrontend().simulate();
                frontend.showWarning(e.getMessage());
                frontend.typeWriter("Load game failed! Resume to previous session.", true);
            }
        }
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
