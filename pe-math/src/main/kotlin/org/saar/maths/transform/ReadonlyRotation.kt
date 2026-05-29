package org.saar.maths.transform

import org.joml.Quaternionfc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.jproperty.ObservableValue

interface ReadonlyRotation : ObservableValue<Quaternionfc> {
    val eulerAngles: Vector3fc
    val direction: Vector3f
}
