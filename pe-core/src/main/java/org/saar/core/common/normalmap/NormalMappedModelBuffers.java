package org.saar.core.common.normalmap;

import org.saar.core.model.ElementsMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.core.model.loader.ModelBuffers;
import org.saar.core.model.loader.ModelWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;
import org.saar.lwjgl.opengl.objects.vbos.Vbos;
import org.saar.lwjgl.opengl.utils.BufferWriter;

import java.nio.ByteBuffer;

public abstract class NormalMappedModelBuffers extends AbstractModelBuffers implements ModelBuffers {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);

    private static final Attribute uvCoordAttribute = Attribute.of(1, 2, DataType.FLOAT, false);

    private static final Attribute normalAttribute = Attribute.of(2, 3, DataType.FLOAT, false);

    private static final Attribute tangentAttribute = Attribute.of(3, 3, DataType.FLOAT, false);

    private static final Attribute biTangentAttribute = Attribute.of(4, 3, DataType.FLOAT, false);

    public void load(NormalMappedVertex[] vertices, int[] indices) {
        ModelWriters.writeVertices(getWriter(), vertices);
        ModelWriters.writeIndices(getWriter(), indices);

        updateBuffers();
        deleteBuffers();
    }

    protected abstract NormalMappedModelWriter getWriter();

    public static Mesh toMesh(ByteBuffer dataBuffer, ByteBuffer indexBuffer) {
        final Vao vao = Vao.create();

        final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);
        Vbos.allocateAndStore(vbo, dataBuffer);
        vao.loadVbo(vbo, positionAttribute, uvCoordAttribute,
                normalAttribute, tangentAttribute, biTangentAttribute);

        final IndexBuffer indexVbo = new IndexBuffer(VboUsage.STATIC_DRAW);
        Vbos.allocateAndStore(indexVbo, indexBuffer);
        vao.loadVbo(indexVbo);

        return new ElementsMesh(vao, RenderMode.TRIANGLES, indexBuffer.limit(), DataType.U_INT);
    }

    public static NormalMappedModelBuffers singleDataBuffer(int vertices, int indices) {
        return new SingleDataBuffer(vertices, indices);
    }

    public static NormalMappedModelBuffers multipleDataBuffers(int vertices, int indices) {
        return new MultipleDataBuffers(vertices, indices);
    }

    private static class SingleDataBuffer extends NormalMappedModelBuffers {

        private static final Attribute[] attributes = {
                NormalMappedModelBuffers.positionAttribute,
                NormalMappedModelBuffers.uvCoordAttribute,
                NormalMappedModelBuffers.normalAttribute,
                NormalMappedModelBuffers.tangentAttribute,
                NormalMappedModelBuffers.biTangentAttribute
        };

        private final ElementsMesh model;

        private final NormalMappedModelWriter writer;

        public SingleDataBuffer(int vertices, int indices) {
            final ModelBuffer vertexBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, attributes);
            final ModelBuffer indexBuffer = loadIndexBuffer(
                    new IndexBuffer(VboUsage.STATIC_DRAW), indices);
            this.model = new ElementsMesh(vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

            this.writer = new NormalMappedModelWriter() {
                @Override public BufferWriter getPositionWriter() { return vertexBuffer.getWriter(); }

                @Override public BufferWriter getUvCoordWriter() { return vertexBuffer.getWriter(); }

                @Override public BufferWriter getNormalWriter() { return vertexBuffer.getWriter(); }

                @Override public BufferWriter getTangentWriter() { return vertexBuffer.getWriter(); }

                @Override public BufferWriter getBiTangentWriter() { return vertexBuffer.getWriter(); }

                @Override public BufferWriter getIndexWriter() { return indexBuffer.getWriter(); }
            };
        }

        @Override
        public ElementsMesh getMesh() {
            return this.model;
        }

        @Override
        public NormalMappedModelWriter getWriter() {
            return this.writer;
        }
    }

    private static class MultipleDataBuffers extends NormalMappedModelBuffers {

        private final ElementsMesh model;

        private final NormalMappedModelWriter writer;

        public MultipleDataBuffers(int vertices, int indices) {
            final ModelBuffer positionBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, positionAttribute);
            final ModelBuffer uvCoordBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, uvCoordAttribute);
            final ModelBuffer normalBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, normalAttribute);
            final ModelBuffer tangentBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, tangentAttribute);
            final ModelBuffer biTangentBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, biTangentAttribute);
            final ModelBuffer indexBuffer = loadIndexBuffer(
                    new IndexBuffer(VboUsage.STATIC_DRAW), indices);
            this.model = new ElementsMesh(vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

            this.writer = new NormalMappedModelWriter() {
                @Override public BufferWriter getPositionWriter() { return positionBuffer.getWriter(); }

                @Override public BufferWriter getUvCoordWriter() { return uvCoordBuffer.getWriter(); }

                @Override public BufferWriter getNormalWriter() { return normalBuffer.getWriter(); }

                @Override public BufferWriter getTangentWriter() { return tangentBuffer.getWriter(); }

                @Override public BufferWriter getBiTangentWriter() { return biTangentBuffer.getWriter(); }

                @Override public BufferWriter getIndexWriter() { return indexBuffer.getWriter(); }
            };
        }

        @Override
        public ElementsMesh getMesh() {
            return this.model;
        }

        @Override
        public NormalMappedModelWriter getWriter() {
            return this.writer;
        }
    }

}
