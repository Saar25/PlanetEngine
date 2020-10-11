package org.saar.maths.transform;

import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jproperty.ReadOnlyProperty;

public interface ReadonlyRotation extends ReadOnlyProperty<Quaternionfc> {

    Vector3fc getEulerAngles();

    Vector3f getDirection();

}
