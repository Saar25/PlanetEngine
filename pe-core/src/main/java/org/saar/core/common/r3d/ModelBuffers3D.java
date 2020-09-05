package org.saar.core.common.r3d;

import org.saar.core.model.loader.ModelBuffers;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

interface ModelBuffers3D extends ModelBuffers {

    Attribute[] transformAttributes = new Attribute[]{
            Attribute.ofInstance(2, 4, DataType.FLOAT, false),
            Attribute.ofInstance(3, 4, DataType.FLOAT, false),
            Attribute.ofInstance(4, 4, DataType.FLOAT, false),
            Attribute.ofInstance(5, 4, DataType.FLOAT, false)};

    Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    Attribute coloursAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    void load(Vertex3D[] vertices, int[] indices, Node3D[] instances);

}
