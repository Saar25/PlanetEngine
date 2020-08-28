package org.saar.core.renderer.r3d;

import org.saar.core.model.InstancedElementsModel;
import org.saar.core.model.Model;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.maths.objects.Transform;

public class ModelBuffers3DOneVbo extends AbstractModelBuffers<Node3D, Vertex3D> implements ModelBuffers3D {

    private final ModelBuffer instanceBuffer;
    private final ModelBuffer vertexBuffer;
    private final ModelBuffer indexBuffer;

    private final Model model;

    public ModelBuffers3DOneVbo(int vertices, int indices, int instances) {
        this.instanceBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW),
                instances, transformAttributes);
        this.vertexBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW),
                vertices, positionAttribute, coloursAttribute);
        this.indexBuffer = loadIndexBuffer(new IndexBuffer(VboUsage.STATIC_DRAW), indices);

        this.model = new InstancedElementsModel(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT, instances);
    }

    @Override
    public void writeInstance(Node3D instance) {
        final Transform transform = instance.getTransform();
        this.instanceBuffer.getWriter().write(transform.getTransformationMatrix());
    }

    @Override
    public void writeVertex(Vertex3D vertex) {
        this.vertexBuffer.getWriter().write(vertex.getPosition3f());
        this.vertexBuffer.getWriter().write(vertex.getColour3f());
    }

    @Override
    public void writeIndex(int index) {
        this.indexBuffer.getWriter().write(index);
    }

    @Override
    public void load(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        this.writeInstances(instances);
        this.writeVertices(vertices);
        this.writeIndices(indices);
        this.updateBuffers();
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
