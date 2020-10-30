package org.saar.core.common.r3d;

import org.saar.core.model.Instance;
import org.saar.maths.transform.Transform;

public interface Instance3D extends Instance {

    Transform getTransform();

}
