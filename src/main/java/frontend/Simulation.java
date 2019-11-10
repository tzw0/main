package frontend;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import farmio.Farmio;
import gameassets.Farmer;
import gameassets.Level;
import logic.Parser;
import storage.Storage;

import java.util.ArrayList;

class Simulation {
    private static final int SLEEP_TIME = 300;
    private Farmio farmio;
    private Storage storage;
    private Ui ui;
    private Farmer farmer;
    private static String lastPath;
    private static int lastFrameId;
    private static boolean hadFullscreen;
    private static boolean isFastMode;

    /**
     * Creates a Simulation for the game, farmio.
     * @param farmio the game that is being simulated.
     */
    Simulation(Farmio farmio, Ui ui) {
        this.farmio = farmio;
        this.ui = ui;
        storage = farmio.getStorage();
        farmer = farmio.getFarmer();
        lastPath = "Welcome";
        lastFrameId = 1;
        hadFullscreen = true;
        isFastMode = false;
    }

    /**
     * Changes the ui used for simulation.
     * @param ui the new ui to be used
     */
    void setUi(Ui ui) {
        this.ui = ui;
    }

    /**
     * Sets simulations to be run in fast mode.
     */
    public static void setFastMode() {
        isFastMode = !isFastMode;
    }

    /**
     * shows an array list of strings in the frame. Every other simulation method converges to this method.
     * @param unformattedFrame the list of strings to be shown.
     */
    public void simulate(ArrayList<String> unformattedFrame, boolean isFullscreen) {
        refresh();
        if (isFullscreen) {
            ui.show(GameConsole.blankConsole(unformattedFrame, GameConsole.FULL_CONSOLE_WIDTH,
                    GameConsole.FULL_CONSOLE_HEIGHT));
        } else {
            ui.show(GameConsole.fullConsole(unformattedFrame, farmer, farmio.getLevel().getGoals(),
                    farmio.getLevel().getObjective(), GameConsole.FRAME_SECTION_WIDTH,
                    GameConsole.FRAME_SECTION_HEIGHT));
        }
    }

    /**
     * Shows the ascii art of a file as a simulation.
     * @param framePath The directory of where the file is found.
     * @param frameId The frame number to be shown.
     * @param isFullscreen if the ascii image is to be shown as the full console or within the frame section.
     * @throws FarmioFatalException if the file cannot be found.
     */
    void simulate(String framePath, int frameId, boolean isFullscreen) throws FarmioFatalException {
        lastPath = framePath;
        lastFrameId = frameId;
        hadFullscreen = isFullscreen;
        simulate(storage.loadFrame(framePath, frameId), isFullscreen);
    }

    /**
     * Simulates from startFrame to endFrame like an animation.
     * @param framePath The directory of where the files are found.
     * @param startFrame The starting frame number.
     * @param endFrame The ending frame number.
     * @param isFullscreen if the ascii image is to be shown as the full console or within the frame section.
     * @throws FarmioFatalException if any file cannot be found.
     */
    void simulate(String framePath, int startFrame, int endFrame, boolean isFullscreen)
            throws FarmioFatalException {
        if (startFrame <= endFrame) {
            for (int i = startFrame; i <= endFrame; i++) {
                simulate(framePath, i, isFullscreen);
            }
        } else {
            for (int i = startFrame; i >= endFrame; i--) {
                simulate(framePath, i, isFullscreen);
            }
        }
    }

    /**
     * Simulates a file without full screen, within the frame section.
     * @param framePath the directory of where the file is found.
     * @param frameId the frame numer to be shown.
     * @throws FarmioFatalException if the file cannot be found.
     */
    void simulate(String framePath, int frameId) throws FarmioFatalException {
        simulate(framePath, frameId, false);
    }

    /**
     * Simulates the last file simulated.
     * @throws FarmioFatalException if the file cannot be found.
     */
    void simulate() throws FarmioFatalException {
        simulate(lastPath, lastFrameId, hadFullscreen);
    }

    /**
     * Simulates a file without full screen from start frame to end frame.
     * @param framePath the directory of where the files are found.
     * @param startFrame the starting frame number.
     * @param endFrame the ending frame number.
     * @throws FarmioFatalException if any file cannot be found.
     */
    void simulate(String framePath, int startFrame, int endFrame) throws FarmioFatalException {
        if (isFastMode) {
            simulate();
            return;
        }
        simulate(framePath, startFrame, endFrame, false);
    }

    /**
     * Updates storage, ui and farmer, clears the screen and gives a short delay.
     */
    private void refresh() {
        storage = farmio.getStorage();
        farmer = farmio.getFarmer();
        ui.sleep(SLEEP_TIME);
        ui.clearScreen();
    }

    /**
     * Prints the Narrative of a given level with a simulation instance.
     * @throws FarmioFatalException if simulation file is not found
     * @throws FarmioException if error in parsing input
     */
    void showNarrative() throws FarmioFatalException, FarmioException {
        storage = farmio.getStorage();
        farmer = farmio.getFarmer();
        Level level = farmio.getLevel();
        int frameId = 0;
        int lastFrameId = level.getNarratives().size() - 1;
        for (String narrative: level.getNarratives()) {
            String userInput;
            userInput = ui.getInput();
            while (!userInput.equals("") && !userInput.toLowerCase().equals("skip")
                    && !userInput.toLowerCase().equals("exit") && !userInput.toLowerCase().equals("quit game")) {
                simulate();
                ui.showWarning("Invalid Command for story mode!");
                ui.show("Story segment only accepts [skip] to skip the story or pressing [ENTER] to continue "
                        + "with the narrative.\nIf you wish to use other logic.commands, enter [skip] followed by "
                        + "entering the command of your choice.");
                userInput = ui.getInput();
            }
            if (userInput.toLowerCase().equals("exit") || userInput.toLowerCase().equals("quit game")) {
                Parser.parse(userInput, Farmio.Stage.LEVEL_START).execute(farmio);
                return;
            }
            if (userInput.toLowerCase().equals("skip") || frameId == lastFrameId) {
                break;
            }
            simulate(level.getPath(), frameId++);
            ui.typeWriter(narrative, true);
        }
        simulate(level.getPath(), lastFrameId);
        ui.typeWriter(level.getNarratives().get(lastFrameId), false);
        showLevelBegin();
    }

    /**
     * Shows the level begin String.
     */
    private void showLevelBegin() {
        ui.show("\n"
                + " ".repeat(GameConsole.FULL_CONSOLE_WIDTH / 2 - 8)
                + AsciiColours.GREEN
                + AsciiColours.UNDERLINE
                + "[LEVEL BEGIN]"
                + AsciiColours.SANE
                + "\n\n       "
                + "Enter [start] if you are ready to complete the objective or Enter [hint] if you get stuck!");
    }
}