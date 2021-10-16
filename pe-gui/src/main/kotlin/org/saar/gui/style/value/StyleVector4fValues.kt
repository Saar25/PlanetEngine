package org.saar.gui.style.value

import org.joml.Vector4fc

object StyleVector4fValues {

    @JvmStatic
    val inherit: StyleVector4fValue = StyleVector4fValue { it }

    @JvmStatic
    fun of(value: Vector4fc): StyleVector4fValue = StyleVector4fValue { value }

}