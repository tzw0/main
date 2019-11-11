package storage;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import frontend.AsciiColours;
import gameassets.Farmer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import farmio.Farmio;

import java.io.InputStream;
import java.io.Reader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageManager implements Storage {

    private static final String GAME_FILENAME = "save.json";
    private JSONObject jsonFarmer;

    /**
     * Storage constructor to initialise storage object.
     */
    public StorageManager() {
        jsonFarmer = null;
    }

    @Override
    public boolean getSaveExist() {
        return new File(GAME_FILENAME).exists();
    }

    @Override
    public JSONObject loadFarmer() throws FarmioException {
        Reader reader;
        try {
            reader = new FileReader(GAME_FILENAME);
        } catch (FileNotFoundException e) {
            Farmio.LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioException("Game save not found!");
        }
        JSONParser parser = new JSONParser();
        try {
            jsonFarmer = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            Farmio.LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioException("Game save corrupted!");
        }
        double level;
        try {
            level = (Double) jsonFarmer.get("level");
        } catch (Exception e) {
            Farmio.LOGGER.log(Level.WARNING, "Failed level double check. " + e.toString());
            throw new FarmioException("Game level is corrupted!");
        }
        if (!getLevelExist(level)) {
            Farmio.LOGGER.log(Level.INFO, "Detected invalid level: " + level);
            throw new FarmioException("Game level is corrupted!");
        }
        return jsonFarmer;
    }

    @Override
    public JSONObject loadFarmerBackup() throws FarmioException {
        FileWriter file;
        try {
            file = new FileWriter(GAME_FILENAME);
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (Exception e) {
            Farmio.LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioException("Recovery failed!");
        }
        return jsonFarmer;
    }

    @Override
    public boolean storeFarmer(Farmer farmer) {
        FileWriter file;
        try {
            file = new FileWriter(GAME_FILENAME);
            jsonFarmer = farmer.toJson();
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public String storeFarmerPartial(Farmer farmer) {
        if (jsonFarmer == null) {
            return null;
        }
        FileWriter file;
        try {
            file = new FileWriter(GAME_FILENAME);
            jsonFarmer = farmer.updateJson(jsonFarmer);
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (IOException e) {
            return null;
        }
        String path = System.getProperty("user.dir");
        path = path.concat(System.getProperty("os.name").toLowerCase().startsWith("win") ? "\\" : "/");
        path = path.concat(GAME_FILENAME);
        return path;
    }

    @Override
    public ArrayList<String> loadFrame(String path, int frameId)
            throws FarmioFatalException {
        path = "asciiArt/" + path + "/frame" + frameId + ".txt";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResourceStream(path)));
        String line;
        ArrayList<String> frame = new ArrayList<>();
        while (true) {
            try {
                if ((line = bufferedReader.readLine()) == null) {
                    break;
                }
            } catch (IOException e) {
                Farmio.LOGGER.log(Level.WARNING, e.toString());
                throw new FarmioFatalException(formatFatalMessage(path));
            }
            frame.add(line);
        }
        return frame;
    }

    @Override
    public JSONObject getLevel(double level) throws FarmioFatalException {
        String path = "levels/" + level + ".json";
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(new InputStreamReader(getResourceStream(path)));
        } catch (IOException | ParseException e) {
            Farmio.LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioFatalException(formatFatalMessage(path));
        }
    }


    private boolean getLevelExist(double level) {
        String path = "levels/" + level + ".json";
        return getResourceStream(path) != null;
    }

    /**
     * Locate path in resouces folder and return stream to be processed.
     *
     * @param path Path of interest in resouce folder.
     * @return InputStream to be process using InputStreamReader.
     */
    private InputStream getResourceStream(String path) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(path);
    }

    /**
     * Format fatal message to be displayed in ui.
     *
     * @param path Path that caused a FarmioFatalException.
     * @return Formatted error message to be displayed.
     */
    private String formatFatalMessage(String path) {
        return "\"" + path + "\" not found!";
    }
}
