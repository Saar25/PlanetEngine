package org.saar.lwjgl.opengl.constants;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL31;

public enum VboTarget {

    ARRAY_BUFFER(GL15.GL_ARRAY_BUFFER),
    ELEMENT_ARRAY_BUFFER(GL15.GL_ELEMENT_ARRAY_BUFFER),
    UNIFORM_BUFFER(GL31.GL_UNIFORM_BUFFER),
    PACK_BUFFER(GL21.GL_PIXEL_PACK_BUFFER),
    UNPACK_BUFFER(GL21.GL_PIXEL_UNPACK_BUFFER),
    ;

    private static final VboTarget[] values = values();

    private final int value;

    VboTarget(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public static VboTarget valueOf(int id) {
        for (VboTarget value : VboTarget.values) {
            if (value.get() == id) return value;
        }
        return null;
    }

}
