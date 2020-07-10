package org.saar.core.model.data;

import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.IVbo;

public interface ModelData {

    IVbo vbo();

    Attribute[] attributes(int indexOffset);
}
