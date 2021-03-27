package org.saar.lwjgl.opengl.objects.attributes;

public interface AttributeLinker {

    void link(int attributeIndex, int stride, int offset);

    int getBytes();
}
