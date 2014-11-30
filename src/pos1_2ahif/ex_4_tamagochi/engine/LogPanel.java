package pos1_2ahif.ex_4_tamagochi.engine;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * Created by Florian on 21.11.2014.
 */
public class LogPanel extends JScrollPane {
    private final int width;
    private final Dimension preferredSize;
    private Object logFrame;
    private final JComponent logCanvas;

    public LogPanel(int width, int height) {
        this(CacaCanvas.emptyCanvas(width, height, ' ', ' '), width, height);
    }

    private LogPanel(JComponent canvas, int width, int height) {
        super(canvas, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);

        this.logCanvas = canvas;
        preferredSize = logCanvas.getPreferredSize();

        this.width = width;

        this.setBorder(BorderFactory.createEmptyBorder());

        // get size for scroll pane
        this.logFrame = CacaCanvas.frameFromString.invoke(width, "", "", "", ' ', 'a', ' ');
    }

    @Override
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public void add(String msg) {
        logFrame = CacaCanvas.appendStringsToFrame.invoke(logFrame, msg, "", "");
        CacaCanvas.renderCacaFrame.invoke(logCanvas, logFrame);
        logCanvas.scrollRectToVisible(new Rectangle(0, logCanvas.getPreferredSize().height, 0, 0));
    }
}
