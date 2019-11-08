package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import gameassets.Farmer;
import frontend.AsciiColours;

public class CommandDayEnd extends Command {
    /**
     * Sets and shows the end of day and sets the next day.
     * @param farmio the game where stage is changed to DAY_START.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getFrontend().simulate("DayEnd", 0,4);
        farmio.getFrontend().show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE +  "Day Ended" + AsciiColours.SANE);
        farmio.getFrontend().sleep(700);
        Farmer farmer = farmio.getFarmer();
        farmer.nextDay();
        farmio.getFrontend().simulate();
        farmio.getFrontend().sleep(700);
        farmio.setStage(Farmio.Stage.DAY_START);
    }
}
