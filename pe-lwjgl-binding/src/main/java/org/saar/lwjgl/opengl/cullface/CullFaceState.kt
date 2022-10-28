package org.saar.lwjgl.opengl.cullface

import org.saar.lwjgl.opengl.constants.Face

data class CullFaceState(
    val enabled: Boolean,
    val face: Face,
    val order: CullFaceOrder,
) {
    constructor(face: Face, order: CullFaceOrder) : this(true, face, order)
}