package logic;

import farmio.Farmio;
import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;

public class Logic {
    /**
     * Logic component of the game. Receives user input and parses it, then executes the returned Command.
     *
     * @param stage the current game stage
     * @param farmio the farmio game object
     * @throws FarmioFatalException if there is error in parsing user input, or executing any Command
     */
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
