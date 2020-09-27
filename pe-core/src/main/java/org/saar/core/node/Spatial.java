package org.saar.core.node;

import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.transform.Transform;

public abstract class Spatial implements Node {

    private final Transform localTransform = new SimpleTransform();

    public final Transform getLocalTransform() {
        return this.localTransform;
    }

    public Transform getWorldTransform() {
        return getLocalTransform();
    }
}
