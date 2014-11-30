package pos1_2ahif.ex_4_tamagochi.engine;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

import javax.swing.*;
import java.awt.event.ActionListener;

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
    public static final IFn cacaTextField = Clojure.var(CACA, "caca-text-field");
    public static final IFn currentText = Clojure.var(CACA, "current-text");

    public static JComponent emptyCanvas(int width, int height, char foregroundColorCode, char backgroundColorCode) {
        return (JComponent) CacaCanvas.cacaCanvas.invoke(
                CacaCanvas.frameFromString.invoke(
                        width, height,
                        "", "", "",
                        ' ', foregroundColorCode, backgroundColorCode));
    }

    public static JComponent textField(int width, char foregroundColorCode, char backgroundColorCode, ActionListener changeListener, ActionListener returnListener) {
        JComponent textField = (JComponent) CacaCanvas.cacaTextField.invoke(width, foregroundColorCode, backgroundColorCode);
        Clojure.var(CACA, "add-change-listener").invoke(textField, changeListener);
        Clojure.var(CACA, "add-return-listener").invoke(textField, returnListener);
        return textField;
    }
}
