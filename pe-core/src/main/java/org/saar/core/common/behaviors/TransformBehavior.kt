package org.saar.core.common.behaviors

import org.saar.core.behavior.Behavior
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

class TransformBehavior : Behavior {

    val transform: Transform = SimpleTransform()

}