package org.saar.core.renderer.r3d;

import org.saar.core.node.Node;
import org.saar.maths.objects.Transform;

public interface InstanceNode3D extends Node {

    Transform getTransform();

}
