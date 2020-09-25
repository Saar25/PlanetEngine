package org.saar.example.renderer3d;

import org.saar.core.common.r3d.Node3D;
import org.saar.core.node.AbstractNode;
import org.saar.maths.transform.Transform;

public class MyNode extends AbstractNode implements Node3D {

    private final Transform transform = new Transform();

    @Override
    public Transform getTransform() {
        return this.transform;
    }
}
