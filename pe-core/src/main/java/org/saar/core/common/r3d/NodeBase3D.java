package org.saar.core.common.r3d;

import org.saar.core.node.AbstractNode;
import org.saar.maths.transform.SimpleTransform;

public class NodeBase3D extends AbstractNode implements Node3D {

    private final SimpleTransform transform;

    public NodeBase3D() {
        this.transform = new SimpleTransform();
    }

    public NodeBase3D(SimpleTransform transform) {
        this.transform = transform;
    }

    @Override
    public SimpleTransform getTransform() {
        return this.transform;
    }
}
