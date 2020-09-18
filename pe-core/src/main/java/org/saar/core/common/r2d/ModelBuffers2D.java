package org.saar.core.common.r2d;

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

public abstract class ModelBuffers2D extends AbstractModelBuffers implements ModelBuffers {

    private static final Attribute positionAttribute = Attribute.of(0, 2, DataType.FLOAT, false);

    private static final Attribute colourAttribute = Attribute.of(1, 3, DataType.FLOAT, false);

    public final void load(Vertex2D[] vertices, int[] indices) {
        ModelWriters.writeVertices(getWriter(), vertices);
        ModelWriters.writeIndices(getWriter(), indices);

        updateBuffers();
        deleteBuffers();
    }

    protected abstract ModelWriter2D getWriter();

    public static ModelBuffers2D singleDataBuffer(int vertices, int indices) {
        return new SingleDataBuffer(vertices, indices);
    }

    private static class SingleDataBuffer extends ModelBuffers2D {

        private static final Attribute[] attributes = {
                ModelBuffers2D.positionAttribute,
                ModelBuffers2D.colourAttribute
        };

        private final Mesh mesh;

        private final ModelWriter2D writer;

        public SingleDataBuffer(int vertices, int indices) {
            final ModelBuffer vertexBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, attributes);
            final ModelBuffer indexBuffer = loadIndexBuffer(
                    new IndexBuffer(VboUsage.STATIC_DRAW), indices);

            this.mesh = new ElementsMesh(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

            this.writer = new ModelWriter2D(vertexBuffer.getWriter(),
                    vertexBuffer.getWriter(), indexBuffer.getWriter());
        }

        @Override
        public ModelWriter2D getWriter() {
            return this.writer;
        }

        @Override
        public Mesh getMesh() {
            return this.mesh;
        }
    }


}
