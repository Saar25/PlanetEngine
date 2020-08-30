package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawable.DrawableElementsInstanced;
import org.saar.lwjgl.opengl.objects.vaos.IVao;

public class InstancedElementsModel extends ModelBase implements Model {

    public InstancedElementsModel(IVao vao, RenderMode renderMode, int indices, DataType indexType, int instances) {
        super(vao, new DrawableElementsInstanced(renderMode, indices, indexType, 0, instances));
    }
}
