package org.saar.lwjgl.opengl.primitive;

import org.saar.lwjgl.opengl.utils.BufferWriter;

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
}
