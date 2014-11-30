package pos1_2ahif.ex_4_tamagochi.engine;

/**
 * Created by florian on 30.11.14.
 */
public interface Engine {
    void render(Frame graphicsFrame);

    void status(Frame statusFrame);

    void log(Frame message);

    FrameFactory createGraphicsFrame();

    FrameFactory createStatusFrame();

    FrameFactory createLogFrame();
}
