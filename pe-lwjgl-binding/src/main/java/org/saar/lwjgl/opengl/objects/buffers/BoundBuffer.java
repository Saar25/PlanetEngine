package org.saar.lwjgl.opengl.objects.buffers;

import org.saar.lwjgl.opengl.constants.VboTarget;

import java.util.EnumMap;
import java.util.Map;

final class BoundBuffer {

    private static final Map<VboTarget, Integer> bound = new EnumMap<>(VboTarget.class);

    private BoundBuffer() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(VboTarget target, int id) {
        return BoundBuffer.get(target) == id;
    }

    public static void set(VboTarget target, int id) {
        BoundBuffer.bound.put(target, id);
    }

    public static int get(VboTarget target) {
        return BoundBuffer.bound.getOrDefault(target, 0);
    }

}
