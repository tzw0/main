package frontend;

import java.util.Scanner;

public class UiManager implements Ui {
    private Scanner scanner;
    private String clearScreen = "\033c" + "\033[2J";
    private boolean isFastMode = false;


    /**
     * Creates a user interface object.
     */
    UiManager() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Removes the clear screen string if the OS is windows.
     */
    public void removeClearScreen() {
        this.clearScreen = "";
    }

    /**
     * Sets fastmode for simulations.
     */
    @Override
    public void setFastMode() {
        isFastMode = true;
    }

    /**
     * Prints the message in the terminal.
     *
     * @param message to be printed.
     */
    public void show(String message) {
        System.out.println(message);
    }

    /**
     * Prints the message in the terminal without a new line.
     * @param message to be printed.
     */
    private void print(String message) {
        System.out.print(message);
    }

    /**
     * Prints the exit message.
     */
    public void showExit() {
        typeWriter("Goodbye.", false);
    }

    /**
     * Prints an error in the terminal.
     *
     * @param message to be printed as an error.
     */
    public void showError(String message) {
        show("Error: " + message);
    }

    /**
     * Prints a warning in the terminal.
     *
     * @param message as the warning message.
     */
    public void showWarning(String message) {
        show(AsciiColours.RED + "Warning: " + message + AsciiColours.SANE);
    }

    /**
     * Clears the screen.
     */
    public void clearScreen() {
        System.out.println(clearScreen);
    }

    /**
     * Prints a message as an info.
     *
     * @param message as the info message.
     */
    public void showInfo(String message) {
        show(AsciiColours.CYAN + "Info: " + AsciiColours.SANE + message);
    }

    /**
     * Gets user input.
     *
     * @return the user input.
     */
    public String getInput() {
        show("\nInput: ");
        return scanner.nextLine().replace("[", "").replace("]", "");
    }

    /**
     * Delays the program.
     *
     * @param delay time in milliseconds.
     */
    public void sleep(int delay) {
        if (isFastMode && delay >= 200) {
            delay = 50;
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            clearScreen();
            showWarning("Simulator refersh interrupted! Interface may not display correctly.");
        }
    }

    /**
     * Display hints to the levels onto console.
     * @param text hint to be displayed.
     */
    public void showHint(String text) {
        show(AsciiColours.YELLOW
                + "Hint:"
                + AsciiColours.SANE);
        show(text);
        show("~.Enter [Start] when you are ready to complete the objective");
    }


    /**
     * Prints text to the terminal type writer style.
     *
     * @param text to be printed.
     * @param hasPressEnter if 'Press ENTER' should be added to the print.
     */

    public void typeWriter(String text, boolean hasPressEnter) {
        int lineLength = 0;
        if (!text.isBlank()) {
            print(">>> ");
        }
        sleep(150);
        for (int i = 0; i < text.length(); i++) {
            System.out.printf("%c", text.charAt(i));
            lineLength++;
            if (lineLength > GameConsole.FULL_CONSOLE_WIDTH - 10 && text.charAt(i) == ' ') {
                print("\n    ");
                lineLength = 0;
            } else if (text.charAt(i) == '\n') {
                print("    ");
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
}
