import farmio.Farmio;
import farmio.exceptions.FarmioFatalException;
import logic.commands.Command;
import logic.commands.CommandGameNew;
import org.junit.jupiter.api.Test;
import storage.Storage;
import storage.StorageDummy;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandGameNewTest {

    @Test
    public void executeTest(){
        Farmio farmio = new Farmio(false);
        StorageDummy storage = (StorageDummy) farmio.getStorage();
        storage.setFrame(new ArrayList<>());
        try {
            new CommandGameNew().execute(farmio);
            assertEquals(Farmio.Stage.NAME_ADD, farmio.getStage());
        } catch (Exception e) {
            assert(false);
        }
    }
}