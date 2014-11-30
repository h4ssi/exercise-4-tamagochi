package pos1_2ahif.ex_4_tamagochi.engine;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

import javax.swing.*;

/**
 * Created by Florian on 21.11.2014.
 */
public final class CacaCanvas {
    private CacaCanvas() {
    }

    private static final String CACA = "h4ssi.cacacanvas";

    static {
        Clojure.var("clojure.core", "require").invoke(Clojure.read(CACA));
    }

    public static final IFn frameFromString = Clojure.var(CACA, "frame-from-strings");
    public static final IFn cacaCanvas = Clojure.var(CACA, "cacacanvas");
    public static final IFn getColorForChar = Clojure.var(CACA, "char->Color");
    public static final IFn renderCacaFrame = Clojure.var(CACA, "render-caca-frame");
    public static final IFn appendStringsToFrame = Clojure.var(CACA, "append-strings-to-frame");

    public static JComponent emptyCanvas(int width, int height, char foregroundColorCode, char backgroundColorCode) {
        return (JComponent) CacaCanvas.cacaCanvas.invoke(
                CacaCanvas.frameFromString.invoke(
                        width, height,
                        "", "", "",
                        ' ', foregroundColorCode, backgroundColorCode));
    }
}
