package pos1_2ahif.ex_4_tamagochi.engine;

/**
 * Created by florian on 30.11.14.
 */
public final class FrameSegment {
    private final String text;
    private final String fgPattern;
    private final String bgPattern;

    public FrameSegment(String text, String fgPattern, String bgPattern) {
        this.text = text;
        this.fgPattern = fgPattern;
        this.bgPattern = bgPattern;
    }

    public FrameSegment(String text) {
        this(text, null, null);
    }

    public String getText() {
        return text;
    }

    public String getFgPattern() {
        return fgPattern;
    }

    public String getBgPattern() {
        return bgPattern;
    }
}
