package org.saar.core.common.smooth

import org.jproperty.type.FloatProperty
import org.jproperty.type.SimpleFloatProperty
import org.saar.core.mesh.Model
import org.saar.maths.transform.SimpleTransform

class SmoothModel(override val mesh: SmoothMesh, val transform: SimpleTransform) : Model {

    val targetProperty: FloatProperty = SimpleFloatProperty(0f).also {
        it.addListener { e ->
            if (e.newValue.toFloat() < 0) it.value = 0f
            if (e.newValue.toFloat() > 1) it.value = 1f
        }
    }

    var target: Float
        get() = this.targetProperty.value
        set(value) = this.targetProperty.set(value)

    constructor(mesh: SmoothMesh) : this(mesh, SimpleTransform())
}