package org.saar.core.renderer.r3d;

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

public class RenderNode3D extends AbstractNode implements RenderNode {

    private static final Attribute[] attributes = new Attribute[]{
            Attribute.of(0, 3, DataType.FLOAT, true),
            Attribute.of(1, 3, DataType.FLOAT, true),
            /*Attribute.of(3, 4, DataType.FLOAT, true),
            Attribute.of(4, 4, DataType.FLOAT, true),
            Attribute.of(5, 4, DataType.FLOAT, true),
            Attribute.of(6, 4, DataType.FLOAT, true), Transformation matrix */
    };

    private final List<Node3D> nodes;

    private final Vao vao = Vao.create();

    private final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
    private final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);

    private Model model;

    public RenderNode3D(Node3D... nodes) {
        this.nodes = Arrays.asList(nodes);
        this.vao.loadIndexBuffer(this.indexBuffer);
        this.vao.loadDataBuffer(this.dataBuffer, RenderNode3D.attributes);
        update();
    }

    public void update() {
        final NodeWriter3D writer = new NodeWriter3D();
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

        this.model = new ElementsModel(this.vao, indices.length, RenderMode.TRIANGLES);
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
