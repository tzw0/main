package logic.commands;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import gameassets.Farmer;
import storage.Storage;
import frontend.Frontend;

public class CommandLevelReset extends Command {
    /**
     * Resets the level and farmer variables to previous state.
     * @param farmio The game which is reverted to a previous state.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {

        Frontend frontend = farmio.getFrontend();
        Storage storage = farmio.getStorage();
        try {
            farmio.getFarmer().setJson(storage.loadFarmer());
        } catch (FarmioException e) {
            frontend.showWarning(e.getMessage());
            frontend.showInfo("Attempting recovery process.");
            try {
                farmio.getFarmer().setJson(storage.loadFarmerBackup());
            } catch (FarmioException ex) {
                frontend.showError("Recovery process failed!");
                frontend.showInfo("Game cannot continue. Exiting now.");
            }
            frontend.showInfo("Recovery successful.");
        }
        farmio.getFrontend().typeWriter("Level reset successful! Press [ENTER] to continue", false);
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
