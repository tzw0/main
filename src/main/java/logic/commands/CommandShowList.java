package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Frontend;

public class CommandShowList extends Command {
    private String filePath;

    public CommandShowList(String listPath) {
        filePath = listPath;
    }

    /**
     * Shows a list from a file to the user.
     * @param farmio the game where the level is extracted to show only the relevant actions.
     * @throws FarmioFatalException if Simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Frontend frontend = farmio.getFrontend();
        double level = farmio.getFarmer().getLevel();
        frontend.simulate(filePath, (int)(level * 10),false);
        frontend.show("Press [ENTER] to go back to game");
    }

}