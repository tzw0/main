package logic;

import farmio.Farmio;
import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;

public class Logic {

    public static void execute(Farmio.Stage stage, Farmio farmio) throws FarmioFatalException {
        try {
            String userInput = "";
            if (Farmio.Stage.reqInput.contains(stage)) {
                userInput = farmio.getFrontend().getInput();
            }
            Parser.parse(userInput, stage).execute(farmio);
        } catch (FarmioException | FarmioFatalException e) {
            farmio.getFrontend().simulate();
            farmio.getFrontend().showWarning(e.getMessage());
        }
    }
}
