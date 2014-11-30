package pos1_2ahif.ex_4_tamagochi.engine;

/**
 * Created by florian on 30.11.14.
 */
public interface FrameFactory {
    Frame fromStrings(String syms, String fg, String bg);

    Frame fromSegments(FrameSegment... segments);
}
