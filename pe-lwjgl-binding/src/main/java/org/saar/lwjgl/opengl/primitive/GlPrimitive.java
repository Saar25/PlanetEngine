package org.saar.lwjgl.opengl.primitive;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public interface GlPrimitive {

    void loadUniform(int location);

    Attribute[] attribute(int index, boolean normalized);

    void write(BufferWriter buffer);

    DataType getDataType();

    int getComponentCount();
}
