package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawable.DrawableElementsInstanced;
import org.saar.lwjgl.opengl.objects.vaos.IVao;

public class InstancedElementsMesh extends MeshBase implements Mesh {

    public InstancedElementsMesh(IVao vao, RenderMode renderMode, int indices, DataType indexType, int instances) {
        super(vao, new DrawableElementsInstanced(renderMode, indices, indexType, 0, instances));
    }
}
