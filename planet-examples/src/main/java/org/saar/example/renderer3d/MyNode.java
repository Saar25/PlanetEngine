package org.saar.example.renderer3d;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.node.AbstractNode;
import org.saar.core.renderer.r3d.Node3D;
import org.saar.maths.objects.Transform;

public class MyNode extends AbstractNode implements Node3D {

    private final MyModelNode model;
    private final MyInstanceNode instance;

    public MyNode(MyModelNode model, MyInstanceNode instance) {
        this.model = model;
        this.instance = instance;
    }

    @Override
    public Transform getTransform() {
        return this.instance.getTransform();
    }

    @Override
    public ModelVertices<MyVertex> getVertices() {
        return this.model.getVertices();
    }

    @Override
    public ModelIndices getIndices() {
        return this.model.getIndices();
    }
}
