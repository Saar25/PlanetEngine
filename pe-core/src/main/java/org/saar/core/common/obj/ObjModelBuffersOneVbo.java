package org.saar.core.common.obj;

import org.saar.core.model.ElementsModel;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;

public class ObjModelBuffersOneVbo extends AbstractModelBuffers<IObjNode, IObjVertex> implements ObjModelBuffers {

    private static final Attribute[] vertexAttributes = {
            positionAttribute, uvCoordAttribute, normalAttribute
    };

    private final ElementsModel model;

    private final ModelBuffer vertexBuffer;
    private final ModelBuffer indexBuffer;

    private final ObjModelWriter writer;

    public ObjModelBuffersOneVbo(int vertices, int indices) {
        this.vertexBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW), vertices, vertexAttributes);
        this.indexBuffer = loadIndexBuffer(new IndexBuffer(VboUsage.STATIC_DRAW), indices);
        this.model = new ElementsModel(vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

        this.writer = new ObjModelWriter(
                this.vertexBuffer.getWriter(), this.vertexBuffer.getWriter(),
                this.vertexBuffer.getWriter(), this.indexBuffer.getWriter());
    }

    @Override
    public void writeInstance(IObjNode instance) {
    }

    @Override
    public void writeVertex(IObjVertex vertex) {
        this.writer.writeVertex(vertex);
    }

    @Override
    public void writeIndex(int index) {
        this.writer.writeIndex(index);
    }

    @Override
    public void load(IObjVertex[] vertices, int[] indices) {
        writeVertices(vertices);
        writeIndices(indices);
        updateBuffers();
        deleteBuffers();
    }

    @Override
    public ElementsModel getModel() {
        return this.model;
    }
}
