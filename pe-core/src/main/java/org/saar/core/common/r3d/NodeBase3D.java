package org.saar.core.common.r3d;

import org.saar.core.node.AbstractNode;
import org.saar.maths.transform.Transform;

public class NodeBase3D extends AbstractNode implements Node3D {

    private final Transform transform;

    public NodeBase3D() {
        this.transform = new Transform();
    }

    public NodeBase3D(Transform transform) {
        this.transform = transform;
    }

    @Override
    public Transform getTransform() {
        return this.transform;
    }
}
