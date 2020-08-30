package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawable.DrawableArrays;
import org.saar.lwjgl.opengl.objects.vaos.IVao;

public class ArraysModel extends ModelBase implements Model {

    public ArraysModel(IVao vao, RenderMode renderMode, int vertices) {
        super(vao, new DrawableArrays(renderMode, 0, vertices));
    }
}
