package org.saar.core.renderer.r2d;

import org.saar.core.model.ElementsModel;
import org.saar.core.model.Model;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenderNode2D extends AbstractNode implements RenderNode {

    private static final Attribute[] attributes = new Attribute[]{
            Attribute.of(0, 2, DataType.FLOAT, true),
            Attribute.of(1, 3, DataType.FLOAT, true)};

    private final Mesh2D mesh;
    private final List<Node2D> nodes;

    private final Vao vao = Vao.create();

    private final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);

    private Model model;

    public RenderNode2D(Mesh2D mesh, Node2D... nodes) {
        this.mesh = mesh;
        this.nodes = new ArrayList<>(Arrays.asList(nodes));
        init();
    }

    private void init() {
        this.vao.loadVbo(this.indexBuffer);
        this.vao.loadVbo(this.dataBuffer,
                RenderNode2D.attributes);
        this.update();
        this.nodes.clear();
    }

    public void update() {
        final NodeWriter2D writer = new NodeWriter2D();
        writer.write(this.mesh, this.nodes);

        final int[] data = writer.getDataWriter().getDataArray();
        RenderNode2D.write(this.dataBuffer, data);

        final int[] indices = writer.getIndexWriter().getDataArray();
        RenderNode2D.write(this.indexBuffer, indices);

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
