package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawable.DrawableElements;
import org.saar.lwjgl.opengl.objects.vaos.IVao;

public class ElementsModel extends ModelBase implements Model {

    public ElementsModel(IVao vao, RenderMode renderMode, int indices, DataType indexType) {
        super(vao, new DrawableElements(renderMode, indices, indexType, 0));
    }
}
