package pos1_2ahif.ex_4_tamagochi.engine.impl;

import pos1_2ahif.ex_4_tamagochi.engine.api.Engine;
import pos1_2ahif.ex_4_tamagochi.engine.api.FrameFactory;
import pos1_2ahif.ex_4_tamagochi.engine.api.TamagochiLogic;
import pos1_2ahif.ex_4_tamagochi.engine.exception.InitializationException;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Calendar;

/**
 * Created by Florian on 18.11.2014.
 */
public class TamagochiEngine implements Engine {
    private JFrame frame;
    private JComponent graphics;
    private JComponent status;
    private LogPanel logEntries;
    private JComponent textField;

    private char foregroundColorCode;
    private char backgroundColorCode;

    private int graphicsWidth = 16;
    private int graphicsHeight = 8;
    private int statusWidth = 16;
    private int logWidth = 80 - graphicsWidth - statusWidth;
    private int height = 20;

    private TamagochiLogic logic = null;
    private Timer timer = new Timer(0, null);

    public TamagochiEngine(final char foregroundColorCode, final char backgroundColorCode) throws InitializationException {
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
                            onExit();
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            System.out.println("closed...");
                        }
                    });

                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                    graphics = createCacaCanvas(graphicsWidth, graphicsHeight);
                    frame.add(graphics, BorderLayout.LINE_START);

                    status = createCacaCanvas(statusWidth, height);
                    frame.add(status, BorderLayout.CENTER);

                    logEntries = new LogPanel(logWidth, height, foregroundColorCode, backgroundColorCode);

                    frame.add(logEntries, BorderLayout.LINE_END);

                    textField = CacaCanvas.textField(80, foregroundColorCode, backgroundColorCode,
                            new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {

                                }
                            },
                            new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    // on enter:
                                    String command = (String) CacaCanvas.currentText.invoke(textField);
                                    if (command == null || command.isEmpty()) {
                                        return;
                                    }

                                    System.out.println(command);
                                    CacaCanvas.currentText.invoke(textField, "");
                                }
                            });

                    frame.add(textField, BorderLayout.PAGE_END);

                    frame.pack();
                    frame.setResizable(false);
                }
            });
        } catch (InterruptedException e) {
            throw new InitializationException("Engine was interrupted during initialization: " + e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new InitializationException("Engine threw exception during initialization: " + e.getMessage(), e);
        }
    }

    @Override
    public synchronized void start(final TamagochiLogic logic) {
        unloadLogic();

        this.logic = logic;

        this.logic.init();

        frame.setVisible(true);

        timer.setInitialDelay(0);
        timer.setDelay(1000);

        final TamagochiLogic logicClosure = logic;
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logicClosure.update(Calendar.getInstance());
            }
        });

        timer.start();
    }

    private synchronized void onExit() {
        unloadLogic();
    }

    private void unloadLogic() {
        if(this.logic != null) {
            logic.exit();
            timer.setInitialDelay(10000);
            timer.restart();
            timer.stop();
            for (ActionListener l : timer.getActionListeners()) {
                timer.removeActionListener(l);
            }
            this.logic = null;
        }
    }

    @Override
    public void render(final Frame graphicsFrame) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CacaCanvas.renderCacaFrame.invoke(
                        graphics,
                        graphicsFrame.nextFrame(null));
                graphics.repaint();
            }
        });
    }

    @Override
    public void status(final Frame statusFrame) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CacaCanvas.renderCacaFrame.invoke(
                        status,
                        statusFrame.nextFrame(null));
                status.repaint();
            }
        });
    }

    @Override
    public void log(final Frame message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                logEntries.add(message);
            }
        });
    }

    private JComponent createCacaCanvas(int width, int height) {
        return CacaCanvas.emptyCanvas(width, height, foregroundColorCode, backgroundColorCode);
    }

    private Color checkValidColorCode(char colorCode) {
        Color color = (Color) CacaCanvas.getColorForChar.invoke(colorCode);

        if (color == null) {
            throw new IllegalArgumentException(MessageFormat.format("''{0}'' is not a valid color code!", colorCode));
        }

        return color;
    }

    private char getForegroundColorCode() {
        return foregroundColorCode;
    }

    private final void setForegroundColorCode(char foregroundColorCode) {
        Color foregroundColor = checkValidColorCode(foregroundColorCode);
        this.foregroundColorCode = foregroundColorCode;

        frame.setForeground(foregroundColor);
    }

    private char getBackgroundColorCode() {
        return backgroundColorCode;
    }

    private final void setBackgroundColorCode(char backgroundColorCode) {
        Color backgroundColor = checkValidColorCode(backgroundColorCode);
        this.backgroundColorCode = backgroundColorCode;

        frame.setBackground(backgroundColor);
    }

    @Override
    public FrameFactory createGraphicsFrame() {
        return new IndependentFrameFactory(graphicsWidth, graphicsHeight, foregroundColorCode, backgroundColorCode);
    }

    @Override
    public FrameFactory createStatusFrame() {
        return new IndependentFrameFactory(statusWidth, height, foregroundColorCode, backgroundColorCode);
    }

    @Override
    public FrameFactory createLogFrame() {
        return new AppendingFrameFactory(logWidth, height, foregroundColorCode, backgroundColorCode);
    }
}
