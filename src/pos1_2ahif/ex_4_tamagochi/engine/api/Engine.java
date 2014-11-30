package pos1_2ahif.ex_4_tamagochi.engine.api;

import pos1_2ahif.ex_4_tamagochi.engine.impl.Frame;

/**
 * Created by florian on 30.11.14.
 */
public interface Engine {
    void start(TamagochiLogic logic);

    void render(Frame graphicsFrame);

    void status(Frame statusFrame);

    void log(Frame message);

    FrameFactory createGraphicsFrame();

    FrameFactory createStatusFrame();

    FrameFactory createLogFrame();
}
