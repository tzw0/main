package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import gameassets.Level;
import frontend.Ui;

public class CommandTaskHint extends Command {

    /**
     * Print hint or instructions for current level.
     * @param farmio the game which level is used to determine hint.
     * @throws FarmioFatalException if simulation file is not found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Level level = farmio.getLevel();
        farmio.getFrontend().simulate(level.getPath(),level.getNarratives().size() - 1);
        farmio.getFrontend().showHint(farmio.getLevel().getHint());
    }
}