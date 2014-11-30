package pos1_2ahif.ex_4_tamagochi.engine;

import pos1_2ahif.ex_4_tamagochi.engine.exception.InitializationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.renderable.RenderableImageProducer;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

/**
 * Created by Florian on 18.11.2014.
 */
public class Engine {
    private JFrame frame;
    private JComponent graphics;
    private JComponent status;
    private LogPanel logEntries;
    private JComponent textField;
    private char foregroundColorCode;
    private char backgroundColorCode;
    int graphicsWidth = 16;
    int graphicsHeight = 8;
    int statusWidth = 16;
    int logWidth = 80 - graphicsWidth - statusWidth;
    int height = 20;

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
                                    if(command == null || command.isEmpty()) {
                                        return;
                                    }

                                    System.out.println(command);
                                    CacaCanvas.currentText.invoke(textField, "");
                                }
                            });

                    frame.add(textField, BorderLayout.PAGE_END);

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
                CacaCanvas.renderCacaFrame.invoke(
                        graphics,
                        CacaCanvas.frameFromString.invoke(
                                graphicsWidth, graphicsHeight,
                                symbols, foregroundColors, backgroundColors,
                                ' ', foregroundColorCode, backgroundColorCode));
                graphics.repaint();
            }
        });
    }

    public void status(final String symbols, final String foregroundColors, final String backgroundColors) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CacaCanvas.renderCacaFrame.invoke(
                        status,
                        CacaCanvas.frameFromString.invoke(
                                statusWidth, height,
                                symbols, foregroundColors, backgroundColors,
                                ' ', foregroundColorCode, backgroundColorCode));
                status.repaint();
            }
        });
    }

    public void log(final String message) {
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
