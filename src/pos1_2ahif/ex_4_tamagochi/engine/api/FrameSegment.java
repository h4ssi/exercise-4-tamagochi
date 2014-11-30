package pos1_2ahif.ex_4_tamagochi.engine.api;

/**
 * Representation of a Frame/Text Segment used to describe Frames.
 *
 * A Segment consists of a text and a foreground and background color pattern.
 *
 * The text is rendered as is, with the foreground and background pattern repeated as needed
 */
public final class FrameSegment {
    private final String text;
    private final String fgPattern;
    private final String bgPattern;

    /**
     * generates a Segment from a text and a foreground and background color pattern
     *
     * @param text      text do be displayed
     * @param fgPattern the foreground color pattern to render the text
     * @param bgPattern the background color pattern to render the text
     */
    public FrameSegment(String text, String fgPattern, String bgPattern) {
        this.text = text;
        this.fgPattern = fgPattern;
        this.bgPattern = bgPattern;
    }

    /**
     * generates a Segment only from the text. Colors will be chosen automatically
     *
     * @param text text to be displayed
     */
    public FrameSegment(String text) {
        this(text, null, null);
    }

    /**
     * return ths text part of the segment
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * returns the foreground color pattern of the segment
     *
     * @return the foreground color pattern (null means: use default color)
     */
    public String getFgPattern() {
        return fgPattern;
    }

    /**
     * returns the background color pattern of the segment
     *
     * @return the background color pattern (null means: use default color)
     */
    public String getBgPattern() {
        return bgPattern;
    }
}
