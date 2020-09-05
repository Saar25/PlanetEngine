package org.saar.core.common.r2d;

import org.saar.core.model.loader.ModelBuffers;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

interface ModelBuffers2D extends ModelBuffers {

    Attribute[] attributes = new Attribute[]{
            Attribute.of(0, 2, DataType.FLOAT, true),
            Attribute.of(1, 3, DataType.FLOAT, true)};

    void load(Vertex2D[] vertices, int[] indices);

}
