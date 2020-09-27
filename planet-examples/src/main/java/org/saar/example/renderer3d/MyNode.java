package org.saar.example.renderer3d;

import org.saar.core.common.r3d.Node3D;
import org.saar.maths.transform.SimpleTransform;

public class MyNode implements Node3D {

    private final SimpleTransform transform = new SimpleTransform();

    @Override
    public SimpleTransform getTransform() {
        return this.transform;
    }
}
