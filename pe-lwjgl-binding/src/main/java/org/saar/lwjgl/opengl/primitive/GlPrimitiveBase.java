package org.saar.lwjgl.opengl.primitive;

public abstract class GlPrimitiveBase implements GlPrimitive {

    @Override
    public final int getSize() {
        final int components = getComponentCount();
        final int bytes = getDataType().getBytes();
        return components * bytes;
    }

}
