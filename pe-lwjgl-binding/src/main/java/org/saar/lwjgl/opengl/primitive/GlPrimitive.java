package org.saar.lwjgl.opengl.primitive;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.util.DataWriter;

public interface GlPrimitive {

    void loadUniform(int location);

    IAttribute[] attribute(int index, boolean normalized, int instances);

    void write(DataWriter writer);

    DataType getDataType();

    int getComponentCount();

    int getSize();
}
