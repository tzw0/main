package logic.commands;

import farmio.Farmio;
import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;

public class CommandSetFastMode extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getFrontend().setFastMode();
        farmio.getFrontend().typeWriter("Simulations set to run at fast mode", false);
        farmio.getFrontend().sleep(50);
        farmio.getFrontend().simulate();
    }
}
