package frontend;

import farmio.exceptions.FarmioFatalException;

class Menu {

    /**
     * Display menu options onto terminal.
     * @param frontend Formatter for menu design.
     * @param hasSave True if game save file exist.
     * @param canResume True if game can be resumed.
     * @throws FarmioFatalException Fatal error from Simulator and must stop program.
     */
    static void show(Frontend frontend, boolean hasSave, boolean canResume)
            throws FarmioFatalException {
        if (canResume && hasSave) {
            frontend.simulate("Menu", 2, false);
            frontend.typeWriter("Enter the option of your choice\nPress [Enter] to Resume game", false);
        } else if (canResume) {
            frontend.simulate("Menu", 4, false);
            frontend.typeWriter("Enter the option of your choice\nPress [Enter] to Resume game", false);

        } else if (hasSave) {
            frontend.simulate("Menu", 3, true);
            frontend.typeWriter("Enter the option of your choice\nFor example, if you want to create a new game,"
                    + " Enter [New Game]", false);
        } else {
            frontend.simulate("Menu", 0, true);
            frontend.typeWriter("Enter the option of your choice\nFor example, if you want to create a new game,"
                    + " Enter [New Game]", false);
        }
    }
}