package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.constants.VboTarget;

import java.util.EnumMap;
import java.util.Map;

final class BoundVbo {

    private static final Map<VboTarget, Integer> bound = new EnumMap<>(VboTarget.class);

    private BoundVbo() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(VboTarget target, int id) {
        return BoundVbo.get(target) == id;
    }

    public static void set(VboTarget target, int id) {
        BoundVbo.bound.put(target, id);
    }

    public static int get(VboTarget target) {
        return BoundVbo.bound.getOrDefault(target, 0);
    }

}
