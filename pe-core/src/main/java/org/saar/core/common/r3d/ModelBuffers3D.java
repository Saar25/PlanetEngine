package org.saar.core.common.r3d;

import org.saar.core.model.InstancedElementsMesh;
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

public abstract class ModelBuffers3D extends AbstractModelBuffers implements ModelBuffers {

    private static final Attribute[] transformAttributes = new Attribute[]{
            Attribute.ofInstance(3, 4, DataType.FLOAT, false),
            Attribute.ofInstance(4, 4, DataType.FLOAT, false),
            Attribute.ofInstance(5, 4, DataType.FLOAT, false),
            Attribute.ofInstance(6, 4, DataType.FLOAT, false)};

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    private static final Attribute colourAttribute = Attribute.of(2, 3, DataType.FLOAT, true);

    public final void load(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        ModelWriters.writeNodes(getWriter(), instances);
        ModelWriters.writeVertices(getWriter(), vertices);
        ModelWriters.writeIndices(getWriter(), indices);

        updateBuffers();
        deleteBuffers();
    }

    protected abstract ModelWriter3D getWriter();

    public static ModelBuffers3D singleModelBuffer(int vertices, int indices, int instances) {
        return new SingleDataBuffer(vertices, indices, instances);
    }

    private static class SingleDataBuffer extends ModelBuffers3D {

        private static final Attribute[] vertexAttributes = {
                ModelBuffers3D.positionAttribute,
                ModelBuffers3D.normalAttribute,
                ModelBuffers3D.colourAttribute
        };

        private final Mesh mesh;

        private final ModelWriter3D writer;

        public SingleDataBuffer(int vertices, int indices, int instances) {
            final ModelBuffer instanceBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), instances, transformAttributes);
            final ModelBuffer vertexBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, vertexAttributes);
            final ModelBuffer indexBuffer = loadIndexBuffer(
                    new IndexBuffer(VboUsage.STATIC_DRAW), indices);

            this.mesh = new InstancedElementsMesh(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT, instances);

            this.writer = new ModelWriter3D(instanceBuffer.getWriter(), vertexBuffer.getWriter(),
                    vertexBuffer.getWriter(), vertexBuffer.getWriter(), indexBuffer.getWriter());
        }

        @Override
        public ModelWriter3D getWriter() {
            return this.writer;
        }

        @Override
        public Mesh getMesh() {
            return this.mesh;
        }
    }

}
