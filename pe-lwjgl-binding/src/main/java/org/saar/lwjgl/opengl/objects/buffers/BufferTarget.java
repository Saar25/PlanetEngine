package org.saar.lwjgl.opengl.objects.buffers;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL31;

public enum BufferTarget {

    ARRAY(GL15.GL_ARRAY_BUFFER),
    ELEMENT_ARRAY(GL15.GL_ELEMENT_ARRAY_BUFFER),
    UNIFORM(GL31.GL_UNIFORM_BUFFER),
    PACK(GL21.GL_PIXEL_PACK_BUFFER),
    UNPACK(GL21.GL_PIXEL_UNPACK_BUFFER);

    private final int value;

    BufferTarget(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

}
