package org.saar.example.renderer3d;

import org.saar.core.common.r3d.Instance3D;
import org.saar.maths.transform.SimpleTransform;

public class MyInstance implements Instance3D {

    private final SimpleTransform transform = new SimpleTransform();

    @Override
    public SimpleTransform getTransform() {
        return this.transform;
    }
}
