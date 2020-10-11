package org.saar.lwjgl.opengl.objects.buffers;

import java.util.EnumMap;
import java.util.Map;

final class BoundBuffer {

    private static final Map<BufferTarget, Integer> bound = new EnumMap<>(BufferTarget.class);

    private BoundBuffer() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(BufferTarget target, int id) {
        return BoundBuffer.get(target) == id;
    }

    public static void set(BufferTarget target, int id) {
        BoundBuffer.bound.put(target, id);
    }

    public static int get(BufferTarget target) {
        return BoundBuffer.bound.getOrDefault(target, 0);
    }

}
