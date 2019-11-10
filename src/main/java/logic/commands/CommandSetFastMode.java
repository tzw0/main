package logic.commands;

import farmio.Farmio;
import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;

public class CommandSetFastMode extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getFrontend().typeWriter("Fastmode has been toggled", false);
        farmio.getFrontend().sleep(300);
        farmio.getFrontend().setFastMode();
        farmio.getFrontend().simulate();
    }
}
