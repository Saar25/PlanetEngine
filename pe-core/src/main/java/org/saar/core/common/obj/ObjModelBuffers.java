package org.saar.core.common.obj;

import org.saar.core.model.loader.ModelBuffers;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

interface ObjModelBuffers extends ModelBuffers {

    Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);

    Attribute uvCoordAttribute = Attribute.of(1, 2, DataType.FLOAT, false);

    Attribute normalAttribute = Attribute.of(2, 3, DataType.FLOAT, false);

    void load(ObjVertex[] vertices, int[] indices);

}
