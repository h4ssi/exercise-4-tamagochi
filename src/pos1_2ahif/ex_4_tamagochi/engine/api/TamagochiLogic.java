package pos1_2ahif.ex_4_tamagochi.engine.api;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * This is the interface you have to provide
 *
 * Implement the behaviour of your tamagochi and provide the engine
 * with your implementation
 */
public interface TamagochiLogic {
    /**
     * When the engine is loaded up, it calls the init method of your logic.
     *
     * Use this method to setup your logic if you need anything prepared
     *
     * You could use this to prepare some graphics, for instance...
     */
    void init();

    /**
     * when the engine shuts down, it calls the exit method of your logic.
     *
     * Use this, when you need to do some clean up. (Most likely, you will do nothing here)
     */
    void exit();

    /**
     * every second, the engines asks for an update of your tamagochi.
     *
     * It will provide the actual time, so you can make some decicions based on the current time.
     * E.g. Maybe the tamagochi sleeps at night?
     *
     * @param now the current time
     */
    void update(Calendar now);

    /**
     * when the user has entered a command, the engine will tell you
     *
     * you can react to commands by implementing this method
     *
     * @param command the command typed by the user
     */
    void command(String command);

    /**
     * when your tamagochi is started, its current state will be read from a file
     *
     * use this method to restore the state of your tamagochi, when the program is loaded
     *
     * CARE: If your tamagochi is created for the first time, there is no File yet
     * The "fis" parameter will be null!!!
     *
     * @param name     the name of the tamagochi
     * @param birthday the birthday/date of your tamagochi
     * @param fis      the input stream to read from. Maybe Null if tamagochi was newly created
     */
    void load(String name, Calendar birthday, FileInputStream fis);

    /**
     * when your tamagochi is closed, you can save its state to a file
     *
     * use this method to save the state of your tamagochi
     *
     * @param fos the output stream to write your state to
     */
    void store(FileOutputStream fos);

    /**
     * when your tamagochi is started and loaded from file
     *
     * the engine will ask you to do an update for the time the tamagochi was not running
     *
     * this offlineUpdate method will be called with 2 parameters:
     * the time the tamagochi was turned off, and now
     * you have to make sure, you update the state in such a way
     * so that it seems the tamagochi was never turned off
     *
     * @param from the time the tamagochi was turned off
     * @param to   the current time
     */
    void offlineUpdate(Calendar from, Calendar to);
}
