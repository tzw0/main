package farmio;

import gameassets.Farmer;
import gameassets.Level;
import logic.commands.Command;
import logic.commands.CommandWelcome;
import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import frontend.AsciiColours;
import frontend.Frontend;
import logic.Logic;
import storage.Storage;
import storage.StorageDummy;
import storage.StorageManager;

import java.io.IOException;
import java.util.EnumSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Farmio {
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Storage storage;
    private Farmer farmer;
    private Level level;
    private boolean isExit;
    private Stage stage;
    private Frontend frontend;

    /**
     * Farmio constructor used to initiate an instance of Farmio.
     */
    public Farmio(boolean isActual) {
        if (isActual) {
            storage = new StorageManager();
            farmer = new Farmer();
            frontend = new Frontend(this);
        } else {
            storage = new StorageDummy();
            farmer = new Farmer();
            frontend = new Frontend(this);
            frontend.setDummyUi();
        }

        stage = Stage.WELCOME;
        isExit = false;
    }

    private void run() {
        try {
            setupLogger(java.util.logging.Level.INFO);
            LOGGER.log(java.util.logging.Level.INFO, "New game session started.");
            if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
                frontend.removeClearScreen();
                AsciiColours.inActivate();
            }
            Command command;
            command = new CommandWelcome();
            try {
                command.execute(this);
            } catch (FarmioException e) {
                frontend.showWarning(e.getMessage());
            }
            while (!isExit) {
                Logic.execute(stage, this);
            }
        } catch (FarmioFatalException e) {
            frontend.showError(e.getMessage());
            frontend.showInfo("Encounterd fatal error. Exiting program.");
        }
        storage.storeFarmerPartial(farmer);
        frontend.showExit();
    }

    public static void main(String[] args) {
        new Farmio(true).run();
    }

    public enum Stage {
        WELCOME,
        MENU_START,
        NAME_ADD,
        LEVEL_START,
        MENU,
        TASK_ADD,
        DAY_RUNNING,
        CHECK_OBJECTIVES,
        DAY_END,
        DAY_START,
        LEVEL_END,
        LEVEL_FAILED;
        public static EnumSet<Stage> noInput = EnumSet.of(LEVEL_START,
                DAY_RUNNING,
                CHECK_OBJECTIVES,
                DAY_START,
                LEVEL_END,
                LEVEL_FAILED);
        public static EnumSet<Stage> reqInput = EnumSet.complementOf(noInput);
    }

    public Storage getStorage() {
        return storage;
    }

    public Frontend getFrontend() {
        return frontend;
    }


    public Farmer getFarmer() {
        return farmer;
    }

    public Stage getStage() {
        return stage;
    }

    public Level getLevel() {
        return level;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setExit() {
        this.isExit = true;
    }

    private void setupLogger(java.util.logging.Level level) throws FarmioFatalException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }
        logger.setLevel(level);
        FileHandler handler;
        try {
            handler = new FileHandler("farmio.log");
        } catch (IOException e) {
            throw new FarmioFatalException("Failed to access \'farmio.log\'."
                    + "\nPlease try running farmio in another directory.");
        }
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    }
}