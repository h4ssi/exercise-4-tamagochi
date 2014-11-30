package pos1_2ahif.ex_4_tamagochi.engine.impl;

import java.util.List;

/**
 * Created by florian on 30.11.14.
 */
public class AppendingFrameFactory extends AbstractFrameFactory {
    public AppendingFrameFactory(Integer width, Integer height, char defaultFgColorCode, char defaultBgColorCode) {
        super(width, height, defaultFgColorCode, defaultBgColorCode);
    }

    @Override
    public Frame fromStrings(final String syms, final String fg, final String bg) {
        return new Frame() {
            @Override
            Object nextFrame(Object prev) {
                return CacaCanvas.appendStringsToFrame.invoke(
                        prev, syms, fg, bg
                );
            }
        };
    }

    @Override
    protected Frame fromSegments(final List<Object> cacaSegments) {
        return new Frame() {
            @Override
            Object nextFrame(Object prev) {
                return CacaCanvas.appendSegmentsToFrame.invoke(
                        prev, cacaSegments
                );
            }
        };
    }
}
