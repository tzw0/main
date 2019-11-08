import gameassets.Farmer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import storage.Storage;
import storage.StorageManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageManagerTest {

    private static final String FARMER_JSON_1 = "{\"gold\":826,\"farm_chicken\":{\"chicken\":916,"
            + "\"egg\":4296,\"fullChicken\":21},\"level\":1.1,\"task_current\":-1,\"farm_wheat\":{\"seed\""
            + ":991,\"wheat\":18,\"grain\":3,\"seedling\":1},\"name\":\"FAME\",\"task_status_fail\":false,\"l"
            + "ocation\":\"WheatFarm\",\"farm_cow\":{\"milk\":888,\"cow\":437,\"fullcow\":381},\"task_list\":"
            + "[{\"condition\":{\"condition_type\":\"BOOLEAN\",\"condition_boolean_type\":\"TRUE\"},\"action\""
            + ":\"gotoMarket\",\"type\":\"DO\"}],\"day\":903}";

    private static final String FARMER_JSON_2 = "{\"gold\":0,\"farm_chicken\":{\"chicken\":0,"
            + "\"egg\":0,\"fullChicken\":0},\"level\":0.0,\"task_current\":-1,\"farm_wheat\":{\"seed\""
            + ":0,\"wheat\":0,\"grain\":0,\"seedling\":0},\"name\":\"FAM\",\"task_status_fail\":false,\"l"
            + "ocation\":\"WheatFarm\",\"farm_cow\":{\"milk\":0,\"cow\":0,\"fullcow\":0},\"task_list\":"
            + "[{\"condition\":{\"condition_type\":\"BOOLEAN\",\"condition_boolean_type\":\"TRUE\"},\"action\""
            + ":\"gotoMarket\",\"type\":\"DO\"}],\"day\":0}";

    Storage storage = new StorageManager();

    @Test
    void getSaveExistTest(){
        boolean isExist = new File("save.json").exists();
        assertEquals(isExist, storage.getSaveExist());
    }

    @Test
    void storeFarmerTest(){
        String checksum_expect = "f475115ca265ceede2bf68022624072b327d5733cc69bf0309d1d778393b14cb";
        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(FARMER_JSON_1);
            Farmer farmer = new Farmer(object);
            storage.storeFarmer(farmer);
            String checksum = getChecksum("save.json");
            assertEquals(checksum_expect, checksum);
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    void loadFarmerTest(){
        try{
            JSONParser parser = new JSONParser();
            JSONObject objectStore = (JSONObject) parser.parse(FARMER_JSON_1);
            Farmer farmer = new Farmer(objectStore);
            storage.storeFarmer(farmer);
            JSONObject objectLoad = storage.loadFarmer();
            assertEquals(objectStore.toString(), objectLoad.toString());
        } catch (Exception e){
            assert(false);
        }
    }

    @Test
    void loadFarmerBackupTest(){
        try{
            JSONParser parser = new JSONParser();
            JSONObject objectStore = (JSONObject) parser.parse(FARMER_JSON_1);
            Farmer farmer = new Farmer(objectStore);
            storage.storeFarmer(farmer);
            JSONObject objectLoad = storage.loadFarmerBackup();
            assertEquals(objectStore.toString(), objectLoad.toString());
        } catch (Exception e){
            assert(false);
        }
    }

    @Test
    void storeFarmerPartialTest(){
        try{
            JSONParser parser = new JSONParser();
            JSONObject objectStore = (JSONObject) parser.parse(FARMER_JSON_1);
            Farmer farmer = new Farmer(objectStore);
            storage.storeFarmer(farmer);
            farmer = new Farmer((JSONObject) parser.parse(FARMER_JSON_2));
            storage.storeFarmerPartial(farmer);
            JSONObject objectLoad = storage.loadFarmerBackup();
            assertEquals(objectStore.toString(), objectLoad.toString());
        } catch (Exception e){
            assert(false);
        }
    }

    private String getChecksum(String name) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(name), md)) {
            while (dis.read() != -1) ;
            md = dis.getMessageDigest();
        }
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
