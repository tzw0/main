package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.AsciiColours;
import frontend.Frontend;
import gameassets.Farmer;
import gameassets.Log;
import gameassets.places.Farm;

import static farmio.Farmio.Stage.TASK_ADD;

public class CommandDayStart extends Command {
    /**
     * Shows and sets the start of a new day.
     * @param farmio The game where its stage is set to DAY_RUNNING.
     * @throws FarmioFatalException if simulation file is missing.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        if (farmio.getStage() == TASK_ADD) {
            farmio.getStorage().storeFarmer(farmio.getFarmer());
            Log.clearLogList(farmio);
        }
        Frontend frontend = farmio.getFrontend();
        farmio.getFrontend().simulate("DayStart", 1, 5);
        frontend.show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE + "Day Started!" + AsciiColours.SANE);
        frontend.sleep(300);
        farmio.setStage(Farmio.Stage.DAY_RUNNING);
    }
}