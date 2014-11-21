package pos1_2ahif.ex_4_tamagochi.engine;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import pos1_2ahif.ex_4_tamagochi.engine.exception.InitializationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

/**
 * Created by Florian on 18.11.2014.
 */
public class Engine {
    private static final String CACA = "h4ssi.cacacanvas";

    static {
        Clojure.var("clojure.core", "require").invoke(Clojure.read(CACA));
    }

    private static final IFn frameFromString = Clojure.var(CACA, "frame-from-strings");
    private static final IFn cacaCanvas = Clojure.var(CACA, "cacacanvas");
    private static final IFn getColorForChar = Clojure.var(CACA, "char->Color");
    private static final IFn renderCacaFrame = Clojure.var(CACA, "render-caca-frame");

    private JFrame frame;
    private JComponent view;
    private char foregroundColorCode;
    private char backgroundColorCode;
    int width = 16;
    int height = 8;

    public Engine(final char foregroundColorCode, final char backgroundColorCode) throws InitializationException {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    frame = new JFrame("Tamagochi");

                    setForegroundColorCode(foregroundColorCode);
                    setBackgroundColorCode(backgroundColorCode);

                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            System.out.println("closing...");
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            System.out.println("closed...");
                        }
                    });

                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                    view = createCacaCanvas(width, height);
                    frame.add(view, BorderLayout.CENTER);

                    frame.pack();
                    frame.setResizable(false);

                    frame.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            throw new InitializationException("Engine was interrupted during initialization: " + e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new InitializationException("Engine threw exception during initialization: " + e.getMessage(), e);
        }
    }

    public void render(final String symbols, final String foregroundColors, final String backgroundColors) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                renderCacaFrame.invoke(
                        view,
                        frameFromString.invoke(
                                width, height,
                                symbols, foregroundColors, backgroundColors,
                                ' ', foregroundColorCode, backgroundColorCode));
                view.repaint();
            }
        });
    }

    private JComponent createCacaCanvas(int width, int height) {
        return (JComponent) cacaCanvas.invoke(
                frameFromString.invoke(
                        width, height,
                        "", "", "",
                        ' ', foregroundColorCode, backgroundColorCode));
    }

    private Color checkValidColorCode(char colorCode) {
        Color color = (Color) getColorForChar.invoke(colorCode);

        if (color == null) {
            throw new IllegalArgumentException(MessageFormat.format("''{0}'' is not a valid color code!", colorCode));
        }

        return color;
    }

    public char getForegroundColorCode() {
        return foregroundColorCode;
    }

    public final void setForegroundColorCode(char foregroundColorCode) {
        Color foregroundColor = checkValidColorCode(foregroundColorCode);
        this.foregroundColorCode = foregroundColorCode;

        frame.setForeground(foregroundColor);
    }

    public char getBackgroundColorCode() {
        return backgroundColorCode;
    }

    public final void setBackgroundColorCode(char backgroundColorCode) {
        Color backgroundColor = checkValidColorCode(backgroundColorCode);
        this.backgroundColorCode = backgroundColorCode;

        frame.setBackground(backgroundColor);
    }
}
