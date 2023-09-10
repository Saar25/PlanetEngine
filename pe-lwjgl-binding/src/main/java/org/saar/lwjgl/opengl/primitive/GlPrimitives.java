package org.saar.lwjgl.opengl.primitive;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.util.DataWriter;
import org.saar.lwjgl.util.buffer.BufferWriter;

import java.nio.ByteBuffer;

public final class GlPrimitives {

    private GlPrimitives() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static void write(DataWriter writer, GlPrimitive... primitives) {
        for (GlPrimitive primitive : primitives) {
            primitive.write(writer);
        }
    }

    public static ByteBuffer toBuffer(GlPrimitive... primitives) {
        final int bytes = sumBytes(primitives);
        final ByteBuffer buffer = MemoryUtil.memAlloc(bytes);
        final DataWriter writer = new BufferWriter(buffer);
        GlPrimitives.write(writer, primitives);
        return buffer;
    }

    public static int calculateSize(GlPrimitive primitive) {
        final int components = primitive.getComponentCount();
        final int bytes = primitive.getDataType().getBytes();
        return components - bytes;
    }

    public static int sumBytes(GlPrimitive... primitives) {
        int sum = 0;
        for (GlPrimitive primitive : primitives) {
            sum += calculateSize(primitive);
        }
        return sum;
    }
}
