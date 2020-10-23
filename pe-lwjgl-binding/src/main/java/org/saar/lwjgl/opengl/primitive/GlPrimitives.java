package org.saar.lwjgl.opengl.primitive;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.utils.BufferWriter;

import java.nio.ByteBuffer;

public final class GlPrimitives {

    private GlPrimitives() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static void write(BufferWriter writer, GlPrimitive... primitives) {
        for (GlPrimitive primitive : primitives) {
            primitive.write(writer);
        }
    }

    public static ByteBuffer toBuffer(GlPrimitive... primitives) {
        final int bytes = primitives.length * primitives[0].getSize();
        final ByteBuffer buffer = MemoryUtil.memAlloc(bytes);
        final BufferWriter writer = new BufferWriter(buffer);
        GlPrimitives.write(writer, primitives);
        return buffer;
    }
}
