package org.saar.core.common.primitive;

import org.saar.core.model.InstancedElementsMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.core.model.loader.ModelWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;

public class PrimitiveModelBuffers extends AbstractModelBuffers {

    private final Mesh mesh;

    private final PrimitiveModelWriter writer;

    public PrimitiveModelBuffers(int vertices, int indices, int instances, Attribute[] vertexAttributes, Attribute[] instanceAttributes) {
        final ModelBuffer instanceBuffer = loadDataBuffer(new DataBuffer(
                VboUsage.STATIC_DRAW), instances, instanceAttributes);
        final ModelBuffer vertexBuffer = loadDataBuffer(new DataBuffer(
                VboUsage.STATIC_DRAW), vertices, vertexAttributes);
        final ModelBuffer indexBuffer = loadIndexBuffer(new IndexBuffer(
                VboUsage.STATIC_DRAW), indices);

        this.writer = new PrimitiveModelWriter(instanceBuffer.getWriter(),
                vertexBuffer.getWriter(), indexBuffer.getWriter());

        this.mesh = new InstancedElementsMesh(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT, instances);
    }

    public void load(PrimitiveVertex[] vertices, int[] indices, PrimitiveNode[] instances) {
        if (instances != null) ModelWriters.writeNodes(this.writer, instances);
        if (vertices != null) ModelWriters.writeVertices(this.writer, vertices);
        if (indices != null) ModelWriters.writeIndices(this.writer, indices);

        updateBuffers();
        deleteBuffers();
    }

    @Override
    public Mesh getMesh() {
        return this.mesh;
    }
}
