package org.saar.core.model.vertex;

import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.IVbo;

public interface MeshVertices {

    IVbo vbo();

    Attribute[] attributes();

}
