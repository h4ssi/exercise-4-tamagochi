package pos1_2ahif.ex_4_tamagochi.engine;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Florian on 18.11.2014.
 */
public class Engine {
    static {
        Clojure.var("clojure.core", "require").invoke(Clojure.read("h4ssi.cacacanvas"));
    }

    private static final IFn frameFromString = Clojure.var("h4ssi.cacacanvas", "frame-from-strings");

    public Engine() {
        JFrame frame = new JFrame("Tamagochi");

        frame.setSize(200, 200);
        frame.setResizable(false);

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

        frame.add(createCacaCanvas(), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static JComponent createCacaCanvas() {
        return (JComponent) Clojure.var("h4ssi.cacacanvas", "cacacanvas").invoke(frameFromString.invoke("XX", "aa", "xx"));
    }
}
