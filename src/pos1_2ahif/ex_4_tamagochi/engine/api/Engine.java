package pos1_2ahif.ex_4_tamagochi.engine.api;

import pos1_2ahif.ex_4_tamagochi.engine.impl.Frame;

/**
 * Use this interface to do something actively withing your Tamagochi
 *
 * I.e. display a picture, log some output, display the current status
 *
 * There are also interfaces for creating Ascii-Art graphics
 */
public interface Engine {
    /**
     * Runs the tamagochi.
     *
     * Call this method in your main.
     * It will start up the engine and run the tamagochi simulation.
     *
     * @param logic provide implementation of your tamagochi here
     */
    void start(TamagochiLogic logic);

    /**
     * Display a picture in the upper left of the window.
     *
     * I.e. use this to show the user, how the tamagochi looks like
     *
     * @param graphicsFrame the frame to display (create this frame via createGraphicsFrame)
     */
    void render(Frame graphicsFrame);

    /**
     * Display the current status of the tamagochi (middle section of the window).
     *
     * E.g. display how hungry your tamagochi is, how sleepy etc. etc.
     *
     * @param statusFrame the frame to display (create this frame via createStatusFrame)
     */
    void status(Frame statusFrame);

    /**
     * Log some text (right section of the window)
     *
     * Use this to inform the user about activities of your tamagochi
     *
     * @param message the frame containing the message to display (create this frame via createLogFrame)
     */
    void log(Frame message);

    /**
     * This will show a game over message and delete the current tamagochi.
     *
     * Once the game is lost, call this method.
     */
    void gameOver();

    /**
     * use this method to create Frames to be used with the render method
     *
     * @return an interface for creating Graphic Frames
     */
    FrameFactory createGraphicsFrame();

    /**
     * use this method to create Frames to be used with the status method
     *
     * @return an interface for creating Status Frames
     */
    FrameFactory createStatusFrame();

    /**
     * use this method to create Frames to be used with the log method
     *
     * @return an interface for creating Log Frames
     */
    FrameFactory createLogFrame();
}
