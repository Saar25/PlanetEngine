package org.saar.lwjgl.opengl.stencil

import org.saar.lwjgl.opengl.constants.Comparator

data class StencilFunction(
    val comparator: Comparator,
    val reference: Int,
    val mask: Int = -1
)