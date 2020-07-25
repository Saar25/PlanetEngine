package org.saar.core.node.render;

import org.saar.core.model.Model;
import org.saar.core.model.vertex.ModelBufferWriter;
import org.saar.core.model.vertex.ModelVertex;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;

public class InstancedElementsRenderNode<T extends ModelVertex> extends AbstractNode implements RenderNode {

    private final ModelBufferWriter<T> writer;

    private final Vao vao = Vao.create();

    private final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final DataBuffer instanceBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);

    public InstancedElementsRenderNode(ModelBufferWriter<T> writer) {
        this.writer = writer;
    }

    @Override
    public Model getModel() {
        return null;
    }
}
