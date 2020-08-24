package org.saar.core.renderer.basic;

import org.saar.core.model.ElementsModel;
import org.saar.core.model.Model;
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
        final BasicNodeWriter writer = new BasicNodeWriter();
        writer.write(this.nodes);

        final int[] data = writer.getDataWriter().getDataArray();
        if (this.dataBuffer.getSize() <= data.length) {
            this.dataBuffer.allocateInt(data.length);
        }
        this.dataBuffer.storeData(0, data);

        final int[] indices = writer.getIndexWriter().getDataArray();
        if (this.indexBuffer.getSize() <= indices.length) {
            this.indexBuffer.allocateInt(indices.length);
        }
        this.indexBuffer.storeData(0, indices);

        this.model = new ElementsModel(this.vao, RenderMode.TRIANGLES, indices.length, DataType.U_INT);
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
