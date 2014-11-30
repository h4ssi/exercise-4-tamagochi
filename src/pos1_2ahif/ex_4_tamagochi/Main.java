package pos1_2ahif.ex_4_tamagochi;

import pos1_2ahif.ex_4_tamagochi.engine.api.Engine;
import pos1_2ahif.ex_4_tamagochi.engine.api.FrameSegment;
import pos1_2ahif.ex_4_tamagochi.engine.impl.TamagochiEngine;
import pos1_2ahif.ex_4_tamagochi.engine.exception.InitializationException;

/**
 * Created by Florian on 27.10.2014.
 */
public class Main {
    public static void main(String args[]) throws InitializationException {
        char foregroundColorCode = 'e';
        char backgroundColorCode = '7';

        Engine engine = new TamagochiEngine(
                foregroundColorCode,
                backgroundColorCode);

        engine.start(new MyTamagochiLogic(engine));
    }
}
