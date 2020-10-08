package org.saar.core.common.r3d;

import org.saar.core.node.Node;
import org.saar.maths.transform.Transform;

public interface Node3D extends Node {

    Transform getTransform();

    static Node3D of(Transform transform) {
        return () -> transform;
    }

}
