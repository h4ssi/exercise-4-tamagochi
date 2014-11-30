package pos1_2ahif.ex_4_tamagochi.engine.api;

import java.util.Calendar;

/**
 * Created by florian on 30.11.14.
 */
public interface TamagochiLogic {
    void init();

    void update(Calendar now);

    void command(String command);

    void exit();
}
