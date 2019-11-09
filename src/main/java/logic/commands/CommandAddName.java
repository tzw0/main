package logic.commands;

import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.exceptions.FarmioException;
import frontend.Frontend;


public class CommandAddName extends Command {
    private String name;

    public CommandAddName(String userInput) {
        this.name = userInput.toUpperCase();
    }

    /**
     * Adds the name that the user inputs and saves it as an in-game name.
     * @param farmio The game where its stage is set to NAME_ADD.
     * @throws FarmioFatalException if simulation file is missing.
     * @throws FarmioException if name is invalid.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Frontend frontend = farmio.getFrontend();
        farmio.getFrontend().simulate();
        if (name.equals("MENU")) {
            frontend.typeWriter("Keywords cannot be used as a character name.", false);
            frontend.typeWriter("Enter your name:", false);
        } else if (name.length() <= 15 && name.length() > 0 && (name.matches("[a-zA-Z0-9_]+"))) {
            farmio.getFarmer().inputName(name);
            frontend.typeWriter("Welcome Farmer "
                    + name
                    + ", please press [ENTER] to begin the tutorial"
                    + " or enter [skip] to skip the story", false);
            farmio.setStage(Farmio.Stage.LEVEL_START);
        } else if (name.length() == 0) {
            frontend.typeWriter("Provide a name.", false);
            frontend.typeWriter("Enter a name:", false);
        } else if (name.length() > 15) {
            frontend.typeWriter("Your name can have a maximum of 15 characters.", false);
            frontend.typeWriter("Enter your name:", false);
        } else {
            frontend.typeWriter("Special Characters are not allowed", false);
            frontend.typeWriter("Only alphanumeric and '_' characters are allowed", false);
            frontend.typeWriter("Enter your name:", false);
        }
    }
}