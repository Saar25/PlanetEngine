package org.saar.lwjgl.opengl.attribute;

public interface AttributeLinker {

    void link(int attributeIndex, int stride, int offset);

    int getBytes();
}
