package org.saar.core.renderer.basic;

import org.saar.core.model.ElementsModel;
import org.saar.core.model.Model;
import org.saar.core.model.data.DataWriter;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;

import java.util.Arrays;
import java.util.List;

public class BasicRenderNode extends AbstractNode implements RenderNode {

    private static final Attribute[] attributes = new Attribute[]{
            Attribute.of(0, 2, DataType.FLOAT, true),
            Attribute.of(1, 3, DataType.FLOAT, true)};

    private final List<BasicNode> nodes;

    private final Vao vao = Vao.create();

    private final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);

    private Model model;

    public BasicRenderNode(BasicNode... nodes) {
        this.nodes = Arrays.asList(nodes);
        this.vao.loadIndexBuffer(this.indexBuffer);
        this.vao.loadDataBuffer(this.dataBuffer, BasicRenderNode.attributes);
        update();
    }

    public void update() {
        final DataWriter dataWriter = new DataWriter();
        final DataWriter indexWriter = new DataWriter();
        final BasicNodeWriter writer = new BasicNodeWriter(dataWriter, indexWriter);
        for (BasicNode node : this.nodes) {
            writer.write(node);
        }

        dataWriter.writeTo(this.dataBuffer);
        indexWriter.writeTo(this.indexBuffer);

        this.model = new ElementsModel(this.vao, 4, RenderMode.TRIANGLES);
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
