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

        engine.render(
                engine.createGraphicsFrame().fromStrings(
                        // symbols
                        "                " +
                                "   draw your    " +
                                "   tamagochi    " +
                                "      here      " +
                                "                " +
                                "   eg a fish:   " +
                                "~~°°~~~~~~~~~~~~" +
                                "~~°<*)))u))>{~~~",
                        // foreground colors
                        "                " +
                                "eeeeeeeeeeeeeeee" +
                                "eeeeeeeeeeeeeeee" +
                                "eeeeeeeeeeeeeeee" +
                                "                " +
                                "eeeeeeeeeeeeeeee" +
                                "OpOpoOpOpoOOpoOp" +
                                "popAaAAAaAAaaoOp",
                        // background colors
                        "7777777777777777" +
                                "7777777777777777" +
                                "7777777777777777" +
                                "7777777777777777" +
                                "7777777777777777" +
                                "7777777777777777" +
                                "oooooooooooooooo" +
                                "oooooooooooooooo"));

        engine.status(
                engine.createStatusFrame().fromSegments(new FrameSegment("asdf")));

        engine.log(
                engine.createLogFrame().fromSegments(new FrameSegment("Log entry #1")));
        engine.log(
                engine.createLogFrame().fromSegments(new FrameSegment("Log entry #2")));
    }
}
