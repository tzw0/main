package logic.commands;

import farmio.exceptions.FarmioFatalException;
import gameassets.Farmer;
import farmio.Farmio;
import frontend.Frontend;

public class CommandGameNew extends Command {
    /**
     * Creates a new game.
     * @param farmio The game to be reinitialised as a new game.
     * @throws FarmioFatalException if simulation file is not found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Frontend frontend = farmio.getFrontend();
        farmio.setFarmer(new Farmer());
        farmio.getFrontend().simulate("GameNew", 0, true);
        frontend.typeWriter("New Game Created!", false);
        frontend.typeWriter("Enter your name:", false);
        farmio.setStage(Farmio.Stage.NAME_ADD);
    }
}