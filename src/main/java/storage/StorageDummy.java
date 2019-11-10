package storage;

import farmio.exceptions.FarmioException;
import farmio.exceptions.FarmioFatalException;
import gameassets.Farmer;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class StorageDummy implements Storage {

    boolean hasSaveExist;
    boolean isFarmerStored;
    JSONObject farmer;
    JSONObject farmerBackup;
    String farmerLocation;
    ArrayList<String> frame;
    JSONObject level;

    /**
     * Constructor to initialise StorageDummy object.
     */
    public StorageDummy() {
        hasSaveExist = false;
        isFarmerStored = false;
        farmer = null;
        farmerBackup = null;
        farmerLocation = "";
        frame = null;
        level = null;
    }

    @Override
    public boolean getSaveExist() {
        return hasSaveExist;
    }

    @Override
    public JSONObject loadFarmer() throws FarmioException {
        return farmer;
    }

    @Override
    public JSONObject loadFarmerBackup() throws FarmioException {
        return farmerBackup;
    }

    @Override
    public boolean storeFarmer(Farmer farmer) {
        return isFarmerStored;
    }

    @Override
    public String storeFarmerPartial(Farmer farmer) {
        return farmerLocation;
    }

    @Override
    public ArrayList<String> loadFrame(String path, int frameId)
            throws FarmioFatalException {
        return frame;
    }

    @Override
    public JSONObject getLevel(double level) throws FarmioFatalException {
        return this.level;
    }

    /**
     * Set farmer variable to be retrived using loadFarmer in test cases.
     * @param farmer farmer to be set and retrived.
     */
    public void setFarmer(JSONObject farmer) {
        this.farmer = farmer;
    }

    /**
     * Set frame variable to be retrived using loadFrame in test cases.
     * @param frame frame to be set and retrived.
     */
    public void setFrame(ArrayList<String> frame) {
        this.frame = frame;
    }

    /**
     * Set level variable to be retrived using LoadLevel in test cases.
     * @param level level to be set and retrived.
     */
    public void setLevel(JSONObject level) {
        this.level = level;
    }
}