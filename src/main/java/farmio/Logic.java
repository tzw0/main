package farmio;

import frontend.Simulation;
import frontend.Ui;
import frontend.UiManager;
import exceptions.FarmioException;
import exceptions.FarmioFatalException;

public class Logic {

    public static void execute(Farmio.Stage stage, Farmio farmio) throws FarmioFatalException {
        try {
            String userInput = "";
            if (Farmio.Stage.reqInput.contains(stage)) {
                userInput = farmio.getUi().getInput();
            }
            Parser.parse(userInput, stage).execute(farmio);
        } catch (FarmioException | FarmioFatalException e) {
            farmio.getSimulation().simulate();
            farmio.getUi().showWarning(e.getMessage());
        }
    }
}
