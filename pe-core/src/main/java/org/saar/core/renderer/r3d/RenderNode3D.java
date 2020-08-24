package org.saar.core.renderer.r3d;

import org.saar.core.model.InstancedElementsModel;
import org.saar.core.model.Model;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.*;

import java.util.Arrays;
import java.util.List;

public class RenderNode3D extends AbstractNode implements RenderNode {

    private static final Attribute[] attributes = new Attribute[]{
            Attribute.of(0, 3, DataType.FLOAT, true),
            Attribute.of(1, 3, DataType.FLOAT, true),
    };

    private static final Attribute[] instanceAttributes = new Attribute[]{
            Attribute.ofInstance(2, 4, DataType.FLOAT, false),
            Attribute.ofInstance(3, 4, DataType.FLOAT, false),
            Attribute.ofInstance(4, 4, DataType.FLOAT, false),
            Attribute.ofInstance(5, 4, DataType.FLOAT, false),
    };

    private final Mesh3D modelNode;
    private final List<? extends Node3D> nodes;

    private final Vao vao = Vao.create();

    private final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final DataBuffer instanceBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);

    private Model model;

    public RenderNode3D(Mesh3D modelNode, Node3D... nodes) {
        this.modelNode = modelNode;
        this.nodes = Arrays.asList(nodes);
        init();
    }

    public RenderNode3D(Mesh3D modelNode, List<? extends Node3D> nodes) {
        this.modelNode = modelNode;
        this.nodes = nodes;
        init();
    }

    private void init() {
        this.vao.loadIndexBuffer(this.indexBuffer);
        this.vao.loadDataBuffer(this.dataBuffer,
                RenderNode3D.attributes);
        this.vao.loadDataBuffer(this.instanceBuffer,
                RenderNode3D.instanceAttributes);
        this.update();
        this.nodes.clear();
    }

    public void update() {
        final NodeWriter3D writer = new NodeWriter3D();
        writer.write(this.modelNode, this.nodes);

        final int[] data = writer.getDataWriter().getDataArray();
        RenderNode3D.write(this.dataBuffer, data);

        final int[] instancesData = writer.getInstanceWriter().getDataArray();
        RenderNode3D.write(this.instanceBuffer, instancesData);

        final int[] indices = writer.getIndexWriter().getDataArray();
        RenderNode3D.write(this.indexBuffer, indices);

        this.model = new InstancedElementsModel(this.vao, RenderMode.TRIANGLES,
                indices.length, DataType.U_INT, nodes.size());
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
