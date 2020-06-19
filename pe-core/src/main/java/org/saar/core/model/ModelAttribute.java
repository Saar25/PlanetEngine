package org.saar.core.model;

import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.IVbo;

public interface ModelAttribute {

    int count();

    IVbo vbo();

    Attribute[] attributes();

}
