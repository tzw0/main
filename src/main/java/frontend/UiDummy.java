package frontend;

import exceptions.FarmioFatalException;
import farmio.Level;

public class UiDummy implements Ui {
    public static String uiTestString;
    public static StringBuffer output;

    public UiDummy() {
        uiTestString = "";
        output = new StringBuffer();
    }

    public void show(String message) {
        uiTestString += "show";
    }

    public void showExit() {
        uiTestString += "exit";
    }

    public void showError(String message) {
        uiTestString += "error";
    }

    public void showWarning(String message) {
        uiTestString += "warning";
    }

    public void clearScreen() {
        uiTestString += "clear";
    }

    public void showInfo(String message) {
        uiTestString += "info";
    }

    public String getInput() {
        uiTestString += "input";
        return "";
    }

    public void sleep(int delay) {
        uiTestString += "sleep";
    }

    public void showHint(String text) {
        uiTestString += "hint";
    }

    public void typeWriter(String text, boolean hasPressEnter) {
        uiTestString += "typewriter";
        int lineLength = 0;
        if (!text.isBlank()) {
            output.append(">>> ");
        }
        sleep(150);
        for (int i = 0; i < text.length(); i++) {
            output.append(text.charAt(i));
            lineLength++;
            if (lineLength > GameConsole.FULL_CONSOLE_WIDTH - 10 && text.charAt(i) == ' ') {
                output.append("\n    ");
                lineLength = 0;
            } else if (text.charAt(i) == '\n') {
                output.append("    ");
                lineLength = 0;
            }
            sleep(10);
        }
        if (hasPressEnter) {
            show("\n\n" + " ".repeat(GameConsole.FULL_CONSOLE_WIDTH - GameConsole.USER_CODE_SECTION_WIDTH)
                    + "Press [ENTER] to continue..");
        }
        show("");
    }

    /**
     * Mimics showing a narrative.
     * @param level that the narrative is to be shown.
     * @param simulation that the simulation of the level will utilise.
     * @throws FarmioFatalException if simulation file is missing
     */
    public void showNarrative(Level level, Simulation simulation) throws FarmioFatalException {
        int frameId;
        for (frameId = 0; frameId < level.getNarratives().size() - 1; frameId++) {
            getInput();
            simulation.simulate(level.getPath(), frameId);
            typeWriter("", true);
        }
        getInput();
        simulation.simulate(level.getPath(), frameId);
        typeWriter("", true);
        uiTestString += "levelBegin";
    }
}
