package org.saar.core.renderer.basic;

import org.saar.core.model.ArraysModel;
import org.saar.core.model.InstancedElementsModel;
import org.saar.core.model.Model;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;

import java.util.Arrays;
import java.util.List;

public class BasicRenderNode extends AbstractNode implements RenderNode {

    private final List<BasicNode> nodes;

    private final Vao vao = Vao.create();

    private final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final DataBuffer instanceBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);

    public BasicRenderNode(BasicNode... nodes) {
        this.nodes = Arrays.asList(nodes);
    }

    public void update() {

    }

    @Override
    public Model getModel() {
        return model;
    }
}
