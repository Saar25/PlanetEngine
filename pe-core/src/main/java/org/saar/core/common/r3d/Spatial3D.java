package org.saar.core.common.r3d;

import org.saar.core.node.ChildNode;
import org.saar.core.node.Spatial;
import org.saar.maths.transform.ChildTransform;
import org.saar.maths.transform.Transform;

public class Spatial3D extends Spatial implements ChildNode, Node3D {

    private Model3D parent;
    private Transform worldTransform = getLocalTransform();

    @Override
    public Transform getWorldTransform() {
        return this.worldTransform;
    }

    @Override
    public Transform getTransform() {
        return getWorldTransform();
    }

    @Override
    public Model3D getParent() {
        return this.parent;
    }

    public void setParent(Model3D parent) {
        this.worldTransform = new ChildTransform(getLocalTransform(), parent.getTransform());
        this.parent = parent;
    }
}
