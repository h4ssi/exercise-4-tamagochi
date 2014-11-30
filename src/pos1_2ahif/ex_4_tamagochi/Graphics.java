package pos1_2ahif.ex_4_tamagochi;

import pos1_2ahif.ex_4_tamagochi.engine.api.Engine;
import pos1_2ahif.ex_4_tamagochi.engine.impl.Frame;

/**
 * Created by florian on 30.11.14.
 */
public class Graphics {
    private final Frame defaultFrame;

    public Graphics(Engine engine) {
        defaultFrame = engine.createGraphicsFrame().fromStrings(
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
                        "oooooooooooooooo");
    }

    public Frame getDefaultFrame() {
        return defaultFrame;
    }
}
