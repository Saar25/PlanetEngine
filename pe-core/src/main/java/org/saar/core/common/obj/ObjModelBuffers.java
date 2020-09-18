package org.saar.core.common.obj;

import org.saar.core.model.ElementsMesh;
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

public abstract class ObjModelBuffers extends AbstractModelBuffers implements ModelBuffers {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);

    private static final Attribute uvCoordAttribute = Attribute.of(1, 2, DataType.FLOAT, false);

    private static final Attribute normalAttribute = Attribute.of(2, 3, DataType.FLOAT, false);

    public void load(ObjVertex[] vertices, int[] indices) {
        ModelWriters.writeVertices(getWriter(), vertices);
        ModelWriters.writeIndices(getWriter(), indices);

        updateBuffers();
        deleteBuffers();
    }

    protected abstract ObjModelWriter getWriter();

    public static ObjModelBuffers singleDataBuffer(int vertices, int indices) {
        return new SingleDataBuffer(vertices, indices);
    }

    public static ObjModelBuffers multipleDataBuffers(int vertices, int indices) {
        return new MultipleDataBuffers(vertices, indices);
    }

    private static class SingleDataBuffer extends ObjModelBuffers {

        private static final Attribute[] attributes = {
                ObjModelBuffers.positionAttribute,
                ObjModelBuffers.uvCoordAttribute,
                ObjModelBuffers.normalAttribute
        };

        private final ElementsMesh model;

        private final ObjModelWriter writer;

        public SingleDataBuffer(int vertices, int indices) {
            final ModelBuffer vertexBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, attributes);
            final ModelBuffer indexBuffer = loadIndexBuffer(
                    new IndexBuffer(VboUsage.STATIC_DRAW), indices);
            this.model = new ElementsMesh(vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

            this.writer = new ObjModelWriter(
                    vertexBuffer.getWriter(), vertexBuffer.getWriter(),
                    vertexBuffer.getWriter(), indexBuffer.getWriter());
        }

        @Override
        public ElementsMesh getMesh() {
            return this.model;
        }

        @Override
        public ObjModelWriter getWriter() {
            return this.writer;
        }
    }

    private static class MultipleDataBuffers extends ObjModelBuffers {

        private final ElementsMesh model;

        private final ObjModelWriter writer;

        public MultipleDataBuffers(int vertices, int indices) {
            final ModelBuffer positionBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, positionAttribute);
            final ModelBuffer uvCoordBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, uvCoordAttribute);
            final ModelBuffer normalBuffer = loadDataBuffer(
                    new DataBuffer(VboUsage.STATIC_DRAW), vertices, normalAttribute);
            final ModelBuffer indexBuffer = loadIndexBuffer(
                    new IndexBuffer(VboUsage.STATIC_DRAW), indices);
            this.model = new ElementsMesh(vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

            this.writer = new ObjModelWriter(
                    positionBuffer.getWriter(), uvCoordBuffer.getWriter(),
                    normalBuffer.getWriter(), indexBuffer.getWriter());
        }

        @Override
        public ElementsMesh getMesh() {
            return this.model;
        }

        @Override
        public ObjModelWriter getWriter() {
            return this.writer;
        }
    }

}
