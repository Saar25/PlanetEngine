package org.saar.core.common.flatreflected;

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
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class FlatReflectedModelBuffers extends AbstractModelBuffers implements ModelBuffers {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, false);

    public final void load(FlatReflectedVertex[] vertices, int[] indices) {
        ModelWriters.writeVertices(getWriter(), vertices);
        ModelWriters.writeIndices(getWriter(), indices);

        updateBuffers();
        deleteBuffers();
    }

    protected abstract FlatReflectedModelWriter getWriter();

    public static FlatReflectedModelBuffers singleModelBuffer(int vertices, int indices) {
        return new SingleDataBuffer(vertices, indices);
    }

    private static class SingleDataBuffer extends FlatReflectedModelBuffers {

        private static final Attribute[] vertexAttributes = {
                FlatReflectedModelBuffers.positionAttribute,
                FlatReflectedModelBuffers.normalAttribute,
        };

        private final Mesh mesh;

        private final FlatReflectedModelWriter writer;

        public SingleDataBuffer(int vertices, int indices) {
            final ModelBuffer vertexBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, vertexAttributes);
            final ModelBuffer indexBuffer = loadIndexBuffer(
                    new IndexBuffer(VboUsage.STATIC_DRAW), indices);

            this.mesh = new ElementsMesh(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

            this.writer = new FlatReflectedModelWriter() {
                @Override
                public BufferWriter getPositionWriter() {
                    return vertexBuffer.getWriter();
                }

                @Override
                public BufferWriter getNormalBuffer() {
                    return vertexBuffer.getWriter();
                }

                @Override
                public BufferWriter getIndexBuffer() {
                    return indexBuffer.getWriter();
                }
            };
        }

        @Override
        public FlatReflectedModelWriter getWriter() {
            return this.writer;
        }

        @Override
        public Mesh getMesh() {
            return this.mesh;
        }
    }

}
