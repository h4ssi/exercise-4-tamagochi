package pos1_2ahif.ex_4_tamagochi.engine;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by florian on 30.11.14.
 */
public abstract class AbstractFrameFactory implements FrameFactory {
    protected final Integer width;
    protected final Integer height;
    protected final char defaultFgColorCode;
    protected final char defaultBgColorCode;

    public AbstractFrameFactory(Integer width, Integer height, char defaultFgColorCode, char defaultBgColorCode) {
        this.width = width;
        this.height = height;
        this.defaultFgColorCode = defaultFgColorCode;
        this.defaultBgColorCode = defaultBgColorCode;
    }

    @Override
    public Frame fromSegments(FrameSegment... segments) {
        LinkedList<Object> cacaSegments = new LinkedList<Object>();
        for (FrameSegment s : segments) {
            cacaSegments.add(
                    CacaCanvas.cacaSegment.invoke(
                            s.getText(),
                            s.getFgPattern() == null ? "" + defaultFgColorCode : s.getFgPattern(),
                            s.getBgPattern() == null ? "" + defaultBgColorCode : s.getBgPattern()));
        }

        return fromSegments(cacaSegments);
    }

    protected abstract Frame fromSegments(List<Object> cacaSegments);
}
