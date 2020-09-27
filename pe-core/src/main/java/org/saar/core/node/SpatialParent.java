package org.saar.core.node;

import org.saar.maths.transform.Transform;

import java.util.List;

public abstract class SpatialParent extends Spatial implements ParentNode {

    @Override
    public abstract List<Spatial> getChildren();

    @Override
    public Transform getWorldTransform() {
        return getLocalTransform();
    }
}
