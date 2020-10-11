package org.saar.lwjgl.opengl.fbos;

import java.util.EnumMap;
import java.util.Map;

public class BoundFbo {

    private static final Map<FboTarget, Integer> bound = new EnumMap<>(FboTarget.class);

    private BoundFbo() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(FboTarget target, int id) {
        return BoundFbo.get(target) == id;
    }

    public static void set(FboTarget target, int id) {
        BoundFbo.bound.put(target, id);
        if (target == FboTarget.FRAMEBUFFER) {
            BoundFbo.bound.put(FboTarget.READ_FRAMEBUFFER, id);
            BoundFbo.bound.put(FboTarget.DRAW_FRAMEBUFFER, id);
        }
    }

    public static int get(FboTarget target) {
        return BoundFbo.bound.getOrDefault(target, 0);
    }
}
