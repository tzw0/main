package frontend;

import farmio.Farmio;
import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;

public class Frontend {
    private Simulation simulation;
    private Ui ui;

    public Frontend(Farmio farmio) {
        simulation = new Simulation(farmio);
        ui = new UiManager();
    }

    public void setDummy() {
        ui = new UiDummy();
    }

    public void removeClearScreen() {
        ui.removeClearScreen();
    }

    public void show(String message) {
        ui.show(message);
    }

    public void showExit() {
        ui.showExit();
    }

    public void showError(String message) {
        ui.showError(message);
    }

    public void showWarning(String message) {
        ui.showWarning(message);
    }

    public void clearScreen() {
        ui.clearScreen();
    }

    public void showInfo(String message) {
        ui.showInfo(message);
    }

    public String getInput() {
        return ui.getInput();
    }

    public void sleep(int delay) {
        ui.sleep(delay);
    }

    public void showHint(String text) {
        ui.showHint(text);
    }

    public void typeWriter(String text, boolean hasPressEnter) {
        ui.typeWriter(text, hasPressEnter);
    }

    public void simulate(String framePath, int frameId, boolean isFullscreen) throws FarmioFatalException {
        simulation.simulate(framePath, frameId, isFullscreen);
    }

    public void simulate(String framePath, int startFrame, int endFrame, boolean isFullscreen)
            throws FarmioFatalException {
        simulation.simulate(framePath, startFrame, endFrame, isFullscreen);
    }

    public void simulate(String framePath, int frameId) throws FarmioFatalException {
        simulation.simulate(framePath, frameId);
    }

    public void simulate() throws FarmioFatalException {
        simulation.simulate();
    }

    public void simulate(String framePath, int startFrame, int endFrame) throws FarmioFatalException {
        simulation.simulate(framePath, startFrame, endFrame);
    }

    public void showNarrative() throws FarmioFatalException, FarmioException {
        simulation.showNarrative();
    }

    public void showMenu(boolean hasSave, boolean canResume) throws FarmioFatalException {
        Menu.show(this, hasSave, canResume);
    }
}
