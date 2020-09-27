package org.saar.core.node;

import org.saar.maths.transform.Transform;

public abstract class SpatialChild extends Spatial implements ChildNode {

    @Override
    public abstract Spatial getParent();

    @Override
    public abstract Transform getWorldTransform();
}
