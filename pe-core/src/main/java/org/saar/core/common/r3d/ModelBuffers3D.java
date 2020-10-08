package org.saar.core.common.r3d;

import org.saar.core.model.InstancedElementsMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.loader.*;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class ModelBuffers3D extends MeshBuffersBase implements MeshBuffers {

    private static final Attribute[] transformAttributes = new Attribute[]{
            Attribute.ofInstance(3, 4, DataType.FLOAT, false),
            Attribute.ofInstance(4, 4, DataType.FLOAT, false),
            Attribute.ofInstance(5, 4, DataType.FLOAT, false),
            Attribute.ofInstance(6, 4, DataType.FLOAT, false)};

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    private static final Attribute colourAttribute = Attribute.of(2, 3, DataType.FLOAT, true);

    public final void load(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        beforeLoad(vertices, indices, instances);
        ModelWriters.writeNodes(getWriter(), instances);
        ModelWriters.writeVertices(getWriter(), vertices);
        ModelWriters.writeIndices(getWriter(), indices);

        updateBuffers();
        deleteBuffers();
    }

    protected abstract void beforeLoad(Vertex3D[] vertices, int[] indices, Node3D[] instances);

    protected abstract ModelWriter3D getWriter();

    public static ModelBuffers3D singleModelBuffer() {
        return new SingleDataBuffer();
    }

    private static class SingleDataBuffer extends ModelBuffers3D {

        private static final Attribute[] vertexAttributes = {
                ModelBuffers3D.positionAttribute,
                ModelBuffers3D.normalAttribute,
                ModelBuffers3D.colourAttribute
        };

        private final MeshBuffer instanceBuffer = new MeshBuffer(
                new DataBuffer(VboUsage.STATIC_DRAW), transformAttributes);

        private final MeshBuffer vertexBuffer = new MeshBuffer(
                new DataBuffer(VboUsage.STATIC_DRAW), vertexAttributes);

        private final MeshBuffer indexBuffer = new MeshBuffer(
                new IndexBuffer(VboUsage.STATIC_DRAW));

        private final ModelWriter3D writer = new ModelWriter3D() {
            @Override public BufferWriter getInstanceWriter() { return instanceBuffer.getWriter(); }

            @Override public BufferWriter getPositionWriter() { return vertexBuffer.getWriter(); }

            @Override public BufferWriter getNormalWriter() { return vertexBuffer.getWriter(); }

            @Override public BufferWriter getColourWriter() { return vertexBuffer.getWriter(); }

            @Override public BufferWriter getIndexWriter() { return indexBuffer.getWriter(); }
        };

        public SingleDataBuffer() {
            addMeshBuffer(this.instanceBuffer);
            addMeshBuffer(this.vertexBuffer);
            addMeshBuffer(this.indexBuffer);
        }

        @Override
        protected void beforeLoad(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
            this.instanceBuffer.allocateByCount(indices.length);
            this.vertexBuffer.allocateByCount(vertices.length);
            this.indexBuffer.allocate(indices.length * 4);
        }

        @Override
        public ModelWriter3D getWriter() {
            return this.writer;
        }

        @Override
        public Mesh createMesh() {
            final int indices = this.indexBuffer.getBufferSize() / 4;
            final int instances = this.instanceBuffer.getBufferCount();
            return new InstancedElementsMesh(this.vao, RenderMode.TRIANGLES,
                    indices, DataType.U_INT, instances);
        }
    }

}
