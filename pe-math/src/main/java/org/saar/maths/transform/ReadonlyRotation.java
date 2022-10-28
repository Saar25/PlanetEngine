package org.saar.maths.transform;

import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jproperty.ObservableValue;

public interface ReadonlyRotation extends ObservableValue<Quaternionfc> {

    Vector3fc getEulerAngles();

    Vector3f getDirection();

}
