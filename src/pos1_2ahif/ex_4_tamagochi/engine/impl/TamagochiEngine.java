package pos1_2ahif.ex_4_tamagochi.engine.impl;

import pos1_2ahif.ex_4_tamagochi.engine.api.Engine;
import pos1_2ahif.ex_4_tamagochi.engine.api.FrameFactory;
import pos1_2ahif.ex_4_tamagochi.engine.api.TamagochiLogic;
import pos1_2ahif.ex_4_tamagochi.engine.exception.InitializationException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    private JComponent newGameTextField;
    private CardLayout layout;

    private char foregroundColorCode;
    private char backgroundColorCode;

    private int graphicsWidth = 16;
    private int graphicsHeight = 8;
    private int statusWidth = 16;
    private int logWidth = 80 - graphicsWidth - statusWidth;
    private int height = 20;

    private TamagochiLogic logic = null;
    private Timer timer = new Timer(0, null);

    private String name = "";
    private Calendar birthday = Calendar.getInstance();

    public TamagochiEngine(final char foregroundColorCode, final char backgroundColorCode) throws InitializationException {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    frame = new JFrame("Tamagochi");

                    Color fg = checkValidColorCode(foregroundColorCode);
                    Color bg = checkValidColorCode(backgroundColorCode);

                    TamagochiEngine.this.foregroundColorCode = foregroundColorCode;
                    TamagochiEngine.this.backgroundColorCode = backgroundColorCode;

                    frame.setForeground(fg);
                    frame.setBackground(bg);

                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            onExit();
                        }
                    });

                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                    JPanel mainPanel = new JPanel(new BorderLayout());
                    mainPanel.setForeground(fg);
                    mainPanel.setBackground(bg);

                    graphics = createCacaCanvas(graphicsWidth, graphicsHeight);
                    mainPanel.add(graphics, BorderLayout.LINE_START);

                    status = createCacaCanvas(statusWidth, height);
                    mainPanel.add(status, BorderLayout.CENTER);

                    logEntries = new LogPanel(logWidth, height, foregroundColorCode, backgroundColorCode);
                    mainPanel.add(logEntries, BorderLayout.LINE_END);

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

                                    TamagochiLogic l = logic;
                                    if (l != null) {
                                        l.command(command);
                                    }
                                    CacaCanvas.currentText.invoke(textField, "");
                                }
                            });

                    mainPanel.add(textField, BorderLayout.PAGE_END);

                    JPanel gameOverPanel = new JPanel();
                    gameOverPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                    gameOverPanel.setBackground(bg);
                    gameOverPanel.setForeground(fg);

                    JComponent gameOverLabel = (JComponent)
                            CacaCanvas.cacaCanvas.invoke(
                                    CacaCanvas.frameFromSegments.invoke(
                                            9,
                                            Arrays.asList(
                                                    CacaCanvas.cacaSegment.invoke(
                                                            "GAME OVER",
                                                            "" + foregroundColorCode,
                                                            "" + backgroundColorCode))));
                    gameOverPanel.add(gameOverLabel);

                    JPanel newGamePanel = new JPanel();
                    newGamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                    newGamePanel.setBackground(bg);
                    newGamePanel.setForeground(fg);

                    String prompt = "Choose a name: ";
                    JComponent newGameLabel = (JComponent)
                            CacaCanvas.cacaCanvas.invoke(
                                    CacaCanvas.frameFromSegments.invoke(
                                            prompt.length(),
                                            Arrays.asList(
                                                    CacaCanvas.cacaSegment.invoke(
                                                            prompt,
                                                            "" + foregroundColorCode,
                                                            "" + backgroundColorCode))));
                    newGamePanel.add(newGameLabel);

                    newGameTextField = (JComponent)
                            CacaCanvas.textField(prompt.length(), foregroundColorCode, backgroundColorCode,
                                    new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent actionEvent) {

                                        }
                                    }, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent actionEvent) {
                                            String name = (String) CacaCanvas.currentText.invoke(newGameTextField);
                                            if (name == null) {
                                                return;
                                            }
                                            name = name.trim();
                                            if (name.isEmpty()) {
                                                return;
                                            }
                                            TamagochiLogic l = logic;
                                            if (l != null) {
                                                TamagochiEngine.this.name = name;
                                                TamagochiEngine.this.birthday = Calendar.getInstance();
                                                layout.show(frame.getContentPane(), "loading");
                                                l.load(TamagochiEngine.this.name, TamagochiEngine.this.birthday, null);
                                                startGameLoop();
                                            }
                                        }
                                    });
                    newGamePanel.add(newGameTextField);

                    JPanel loadingPanel = new JPanel();
                    loadingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                    loadingPanel.setBackground(bg);
                    loadingPanel.setForeground(fg);

                    JComponent loadingLabel = (JComponent)
                            CacaCanvas.cacaCanvas.invoke(
                                    CacaCanvas.frameFromSegments.invoke(
                                            10,
                                            Arrays.asList(
                                                    CacaCanvas.cacaSegment.invoke(
                                                            "Loading...",
                                                            "" + foregroundColorCode,
                                                            "" + backgroundColorCode))));
                    loadingPanel.add(loadingLabel);

                    layout = new CardLayout();
                    frame.getContentPane().setLayout(layout);
                    frame.add(mainPanel, "main");
                    frame.add(gameOverPanel, "gameover");
                    frame.add(newGamePanel, "newgame");
                    frame.add(loadingPanel, "loading");

                    layout.show(frame.getContentPane(), "main");
                    frame.pack();
                    layout.show(frame.getContentPane(), "loading");
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

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    frame.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        loadLogic(logic);
    }

    private void startGameLoop() {
        layout.show(frame.getContentPane(), "main");
        textField.requestFocusInWindow();

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

    private static File getFile(TamagochiLogic logic) {
        return new File("tamagochi." + logic.getClass().getSimpleName() + ".txt");
    }

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private void loadLogic(final TamagochiLogic logic) {
        this.logic = logic;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    logic.init();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            File file = getFile(logic);
            final FileInputStream fis = new FileInputStream(file);
            try {
                System.out.println("loading from file " + file.getAbsolutePath() + "...");
                BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

                name = br.readLine();
                String bday = br.readLine();

                birthday = Calendar.getInstance();

                birthday.setTime(TIME_FORMAT.parse(bday));
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        logic.load(name, birthday, fis);
                        startGameLoop();
                    }
                });
            } finally {
                fis.close();
            }
        } catch (FileNotFoundException e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    layout.show(frame.getContentPane(), "newgame");
                    newGameTextField.requestFocusInWindow();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void unloadLogic() {
        if (this.logic != null) {
            try {
                File file = getFile(this.logic);
                FileOutputStream fos = new FileOutputStream(file);
                try {
                    System.out.println("saving to file " + file.getAbsolutePath() + "...");
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos, "UTF-8"), true);
                    pw.println(name);
                    pw.println(TIME_FORMAT.format(birthday.getTime()));

                    logic.store(fos);
                } finally {
                    fos.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

    private char getBackgroundColorCode() {
        return backgroundColorCode;
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
