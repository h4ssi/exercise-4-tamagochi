package pos1_2ahif.ex_4_tamagochi;

import pos1_2ahif.ex_4_tamagochi.engine.api.TamagochiLogic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by florian on 30.11.14.
 */
public class TamagochiLogicAdapter implements TamagochiLogic {
    @Override
    public void init() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update(Calendar now) {

    }

    @Override
    public void offlineUpdate(Calendar from, Calendar to) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void load(String name, Calendar birthday, FileInputStream fis) {

    }

    @Override
    public void store(FileOutputStream fos) {

    }
}
