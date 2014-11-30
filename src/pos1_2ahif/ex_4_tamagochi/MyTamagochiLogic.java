package pos1_2ahif.ex_4_tamagochi;

import pos1_2ahif.ex_4_tamagochi.engine.api.Engine;
import pos1_2ahif.ex_4_tamagochi.engine.api.FrameSegment;

import java.util.Calendar;

/**
 * Created by florian on 30.11.14.
 */
public class MyTamagochiLogic extends TamagochiLogicAdapter {
    private final Engine engine;
    private Graphics graphics;

    private int bubblesBlubbed = 0;
    private int doubleBlubb = 0;

    public MyTamagochiLogic(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        graphics = new Graphics(engine);

        engine.render(graphics.getDefaultFrame());
    }

    private void blubb(Calendar now) {
        engine.log(engine.createLogFrame().fromSegments(
                new FrameSegment(
                        "blubb!",
                        (now.get(Calendar.SECOND) % 2 == 0) ? "f" : "F",
                        " ")));

        bubblesBlubbed++;
    }

    private void drawStatus() {
        engine.status(engine.createStatusFrame().fromSegments(
                // status window is 16 chars wide
                // we print a space " " left and right
                // so we have only 14 chars left
                // -14 means, print 14 chars, and print value left aligned
                // 14 means, print 14 chars, and print value right aligned
                // s is for printing strings
                // d is for printing numbers in _d_ecimal
                new FrameSegment(
                        String.format(" %-14s ", "Bubbles:"),
                        "b",
                        null
                ),
                new FrameSegment(
                        String.format(" %14d ", bubblesBlubbed),
                        "F",
                        null
                )
        ));
    }

    @Override
    public void update(Calendar now) {

        if (doubleBlubb > 0) {
            doubleBlubb--;

            blubb(now);
            blubb(now);
        } else {
            blubb(now);
        }

        drawStatus();
    }

    @Override
    public void command(String command) {
        if ("blubb".equals(command)) {
            for (int i = 0; i < 10; i++) {
                engine.log(
                        engine.createLogFrame().fromSegments(
                                new FrameSegment(
                                        "BLUBB!!",
                                        "Ww",
                                        null)));
                bubblesBlubbed++;
            }
            drawStatus();
        } else if ("doubleblubb".equals(command)) {
            doubleBlubb += 5;
        } else if ("rip".equals(command)) {
            engine.gameOver();
        } else {
            engine.log(
                    engine.createLogFrame().fromSegments(
                            new FrameSegment(
                                    "Blubb????",
                                    "ABC",
                                    null
                            )
                    )
            );
        }
    }
}
