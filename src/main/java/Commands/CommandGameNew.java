package Commands;

import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Level;
import FarmioExceptions.FarmioException;
import FrontEnd.Simulate;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CommandGameNew extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.setFarmer(new Farmer());
        try {
            farmio.setLevel(new Level(farmio.getStorage().getLevel(1), farmio.getFarmer()));
            new Simulate("GameNew", farmio).showFrame(0);
        } catch (IOException | ParseException e) {
            throw new FarmioException("Load level failed! Fatal!");
        }
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}