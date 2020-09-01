package org.saar.core.renderer.obj;

import org.saar.core.model.ElementsModel;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;

public class ObjModelBuffersOneVbo extends AbstractModelBuffers<ObjNode, ObjVertexPrototype> implements ObjModelBuffers {

    private static final Attribute[] vertexAttributes = {
            positionAttribute, uvCoordAttribute, normalAttribute, transformAttributes[0],
            transformAttributes[1], transformAttributes[2], transformAttributes[3]
    };

    private final ElementsModel model;

    private final ModelBuffer vertexBuffer;
    private final ModelBuffer indexBuffer;

    public ObjModelBuffersOneVbo(int vertices, int indices) {
        this.vertexBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW), vertices, vertexAttributes);
        this.indexBuffer = loadIndexBuffer(new IndexBuffer(VboUsage.STATIC_DRAW), indices);
        this.model = new ElementsModel(vao, RenderMode.TRIANGLES, indices, DataType.U_INT);
    }

    @Override
    public void writeInstance(ObjNode instance) {
    }

    @Override
    public void writeVertex(ObjVertexPrototype vertex) {
        this.vertexBuffer.getWriter().write(vertex.getPosition3f());
        this.vertexBuffer.getWriter().write(vertex.getUvCoord2f());
        this.vertexBuffer.getWriter().write(vertex.getNormal3f());
    }

    @Override
    public void writeIndex(int index) {
        this.indexBuffer.getWriter().write(index);
    }

    @Override
    public void load(ObjVertexPrototype[] vertices, int[] indices) {
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
