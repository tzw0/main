package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;

public class CommandWelcome extends Command {

    /**
     * Show Welcome message.
     * @param farmio the game where ui is extracted from.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getFrontend().simulate("Welcome", 1,true);
        farmio.getFrontend().typeWriter("", true);
    }
}
