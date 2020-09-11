package org.saar.core.common.r3d;

import org.saar.core.model.InstancedElementsModel;
import org.saar.core.model.Model;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.core.model.loader.ModelWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;

public class ModelBuffers3DOneVbo extends AbstractModelBuffers implements ModelBuffers3D {

    private static final Attribute[] vertexAttributes = {positionAttribute, coloursAttribute};

    private final Model model;

    private final ModelWriter3D writer;

    public ModelBuffers3DOneVbo(int vertices, int indices, int instances) {
        final ModelBuffer instanceBuffer = loadDataBuffer(new DataBuffer(
                VboUsage.STATIC_DRAW), instances, transformAttributes);
        final ModelBuffer vertexBuffer = loadDataBuffer(new DataBuffer(
                VboUsage.STATIC_DRAW), vertices, vertexAttributes);
        final ModelBuffer indexBuffer = loadIndexBuffer(new IndexBuffer(
                VboUsage.STATIC_DRAW), indices);

        this.model = new InstancedElementsModel(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT, instances);

        this.writer = new ModelWriter3D(
                instanceBuffer.getWriter(), vertexBuffer.getWriter(),
                vertexBuffer.getWriter(), indexBuffer.getWriter());
    }

    @Override
    public void load(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        ModelWriters.writeNodes(this.writer, instances);
        ModelWriters.writeVertices(this.writer, vertices);
        ModelWriters.writeIndices(this.writer, indices);

        updateBuffers();
        deleteBuffers();
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
