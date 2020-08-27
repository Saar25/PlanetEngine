package org.saar.lwjgl.opengl.primitive;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public interface GlPrimitive {

    void loadUniform(int location);

    Attribute[] attribute(int index, boolean normalized);

    void write(int index, int[] buffer);

    void write(int index, float[] buffer);

    DataType getDataType();

    int getComponentCount();
}
