package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Storage;
import Farmio.Level;
import FrontEnd.Ui;
import Exceptions.FarmioException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CommandGameLoad extends Command {
    /**
     * Tries to Load the game and creates a new game if unsuccessful
     * @param farmio the game to be resumed from load state
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        try {
            farmio.setFarmer(new Farmer(storage.loadFarmer()));
            Level level = new Level(storage.getLevel(farmio.getFarmer().getLevel()));
            farmio.setLevel(level);
            farmio.getSimulation().simulate("GameLoad", 0);
            ui.typeWriter("Load Game Success!", true);
        } catch (ParseException | FarmioException e) {
            farmio.getSimulation().simulate("GameNew", 0);
            ui.showWarning("Game save is corrupted!");
            ui.showInfo("Starting a new game.");
        } catch (IOException e) {
            farmio.getSimulation().simulate("GameNew", 0);
            ui.showWarning("No game save detected!");
            ui.showInfo("Starting a new game.");
        }
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
