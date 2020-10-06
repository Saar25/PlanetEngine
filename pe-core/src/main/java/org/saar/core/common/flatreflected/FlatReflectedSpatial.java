package org.saar.core.common.flatreflected;

import org.saar.core.node.ChildNode;
import org.saar.core.node.Spatial;
import org.saar.maths.transform.ChildTransform;
import org.saar.maths.transform.Transform;

public class FlatReflectedSpatial extends Spatial implements ChildNode, FlatReflectedNode {

    private FlatReflectedRenderNode parent;
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
    public FlatReflectedRenderNode getParent() {
        return this.parent;
    }

    public void setParent(FlatReflectedRenderNode parent) {
        this.worldTransform = new ChildTransform(getLocalTransform(), parent.getTransform());
        this.parent = parent;
    }
}
