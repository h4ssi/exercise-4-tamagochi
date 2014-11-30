package pos1_2ahif.ex_4_tamagochi.engine;

import java.util.List;

/**
 * Created by florian on 30.11.14.
 */
public final class IndependentFrameFactory extends AbstractFrameFactory {
    IndependentFrameFactory(Integer width, Integer height, char defaultFgColorCode, char defaultBgColorCode) {
        super(width, height, defaultFgColorCode, defaultBgColorCode);
    }

    @Override
    public Frame fromStrings(String syms, String fg, String bg) {
        final Object frame = CacaCanvas.frameFromString.invoke(
                width, height,
                syms, fg, bg,
                ' ', defaultFgColorCode, defaultBgColorCode);
        return new Frame() {
            @Override
            Object nextFrame(Object prev) {
                return frame;
            }
        };

    }

    @Override
    protected Frame fromSegments(List<Object> cacaSegments) {
        final Object frame = CacaCanvas.frameFromSegments.invoke(width, cacaSegments, ' ', defaultFgColorCode, defaultBgColorCode);
        return new Frame() {
            @Override
            Object nextFrame(Object prev) {
                return frame;
            }
        };
    }
}
