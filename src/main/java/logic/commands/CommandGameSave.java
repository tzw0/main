package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Frontend;

public class CommandGameSave extends Command {
    /**
     * Saves the game.
     * @param farmio the game to be saved.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Frontend frontend = farmio.getFrontend();
        String location = farmio.getStorage().storeFarmerPartial(farmio.getFarmer());
        if (location != null) {
            farmio.getFrontend().simulate();
            frontend.show("Game saved successfully!\nGame save location:\n" + location);
        } else {
            farmio.getFrontend().simulate();
            frontend.show("Game save failed!! Try again later.");
        }
    }
}
