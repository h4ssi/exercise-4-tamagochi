package pos1_2ahif.ex_4_tamagochi.engine.impl;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import java.awt.Color;
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

    public LogPanel(int width, int height, char foreGroundColorCode, char backGroundColorCode) {
        this(CacaCanvas.emptyCanvas(width, height, foreGroundColorCode, backGroundColorCode), width, height, foreGroundColorCode, backGroundColorCode);
    }

    private LogPanel(JComponent canvas, int width, int height, char foreGroundColorCode, char backGroundColorCode) {
        super(canvas, VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_NEVER);

        this.getViewport().setBackground((Color) CacaCanvas.getColorForChar.invoke(backGroundColorCode));

        this.logCanvas = canvas;
        preferredSize = logCanvas.getPreferredSize();

        this.width = width;

        this.setBorder(BorderFactory.createEmptyBorder());

        // get size for scroll pane
        this.logFrame = CacaCanvas.frameFromString.invoke(width, "", "", "", ' ', foreGroundColorCode, backGroundColorCode);
        CacaCanvas.renderCacaFrame.invoke(logCanvas, logFrame);
    }

    @Override
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public void add(Frame frameToAppend) {
        logFrame = frameToAppend.nextFrame(logFrame);
        CacaCanvas.renderCacaFrame.invoke(logCanvas, logFrame);
        logCanvas.revalidate();
        logCanvas.scrollRectToVisible(new Rectangle(0, logCanvas.getPreferredSize().height, 0, 0));
    }
}
