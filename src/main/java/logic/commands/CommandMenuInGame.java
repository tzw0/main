package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;

public class CommandMenuInGame extends Command {

    /**
     * Shows the menu.
     * @param farmio the game which stage is set as MENU.
     * @throws FarmioFatalException if simulation file is missing.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getFrontend().showMenu(farmio.getStorage().getSaveExist(), true);
    }
}
