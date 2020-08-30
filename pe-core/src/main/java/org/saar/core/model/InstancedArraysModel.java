package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawable.DrawableArraysInstanced;
import org.saar.lwjgl.opengl.objects.vaos.IVao;

public class InstancedArraysModel extends ModelBase implements Model {

    public InstancedArraysModel(IVao vao, RenderMode renderMode, int vertices, int instances) {
        super(vao, new DrawableArraysInstanced(renderMode, 0, vertices, instances));
    }
}
