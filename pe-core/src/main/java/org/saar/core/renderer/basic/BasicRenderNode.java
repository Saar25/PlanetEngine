package org.saar.core.renderer.basic;

import org.saar.core.model.ElementsModel;
import org.saar.core.model.Model;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.*;

import java.util.Arrays;
import java.util.List;

public class BasicRenderNode extends AbstractNode implements RenderNode {

    private static final Attribute[] attributes = new Attribute[]{
            Attribute.of(0, 2, DataType.FLOAT, true),
            Attribute.of(1, 3, DataType.FLOAT, true)};

    private final BasicMesh mesh;
    private final List<BasicNode> nodes;

    private final Vao vao = Vao.create();

    private final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);

    private Model model;

    public BasicRenderNode(BasicMesh mesh, BasicNode... nodes) {
        this.mesh = mesh;
        this.nodes = Arrays.asList(nodes);
        init();
    }

    private void init() {
        this.vao.loadIndexBuffer(this.indexBuffer);
        this.vao.loadDataBuffer(this.dataBuffer,
                BasicRenderNode.attributes);
        this.update();
        this.nodes.clear();
    }

    public void update() {
        final BasicNodeWriter writer = new BasicNodeWriter();
        writer.write(this.mesh, this.nodes);

        final int[] data = writer.getDataWriter().getDataArray();
        BasicRenderNode.write(this.dataBuffer, data);

        final int[] indices = writer.getIndexWriter().getDataArray();
        BasicRenderNode.write(this.indexBuffer, indices);

        this.model = new ElementsModel(this.vao, RenderMode.TRIANGLES, indices.length, DataType.U_INT);
    }

    private static void write(WriteableVbo vbo, int[] data) {
        if (vbo.getSize() <= data.length) {
            vbo.allocateInt(data.length);
        }
        vbo.storeData(0, data);
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
