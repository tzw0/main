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

    public StorageDummy(){
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

    public void setHasSaveExist(boolean hasSaveExist) {
        this.hasSaveExist = hasSaveExist;
    }

    public void setFarmerStored(boolean farmerStored) {
        isFarmerStored = farmerStored;
    }

    public void setFarmer(JSONObject farmer) {
        this.farmer = farmer;
    }

    public void setFarmerBackup(JSONObject farmerBackup) {
        this.farmerBackup = farmerBackup;
    }

    public void setFarmerLocation(String farmerLocation) {
        this.farmerLocation = farmerLocation;
    }

    public void setFrame(ArrayList<String> frame) {
        this.frame = frame;
    }

    public  void setLevel(JSONObject jsonObject){
        level = jsonObject;
    }
}