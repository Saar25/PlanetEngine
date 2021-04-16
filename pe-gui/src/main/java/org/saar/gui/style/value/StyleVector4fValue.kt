package org.saar.gui.style.value

import org.joml.Vector4fc

fun interface StyleVector4fValue {
    fun compute(parent: Vector4fc): Vector4fc
}