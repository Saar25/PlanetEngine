package org.saar.core.common.obj;

import org.saar.core.model.ElementsMesh;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.core.model.loader.ModelWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;

public class ObjModelBuffersOneVbo extends AbstractModelBuffers implements ObjModelBuffers {

    private static final Attribute[] vertexAttributes = {
            positionAttribute, uvCoordAttribute, normalAttribute
    };

    private final ElementsMesh model;

    private final ObjModelWriter writer;

    public ObjModelBuffersOneVbo(int vertices, int indices) {
        final ModelBuffer vertexBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW), vertices, vertexAttributes);
        final ModelBuffer indexBuffer = loadIndexBuffer(new IndexBuffer(VboUsage.STATIC_DRAW), indices);
        this.model = new ElementsMesh(vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

        this.writer = new ObjModelWriter(
                vertexBuffer.getWriter(), vertexBuffer.getWriter(),
                vertexBuffer.getWriter(), indexBuffer.getWriter());
    }

    @Override
    public void load(ObjVertex[] vertices, int[] indices) {
        ModelWriters.writeVertices(this.writer, vertices);
        ModelWriters.writeIndices(this.writer, indices);

        updateBuffers();
        deleteBuffers();
    }

    @Override
    public ElementsMesh getModel() {
        return this.model;
    }
}
