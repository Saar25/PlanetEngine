package org.saar.lwjgl.opengl.cullface

data class CullFaceState(
    val enabled: Boolean,
    val face: CullFaceValue,
    val order: CullFaceOrder,
) {
    constructor(face: CullFaceValue, order: CullFaceOrder) : this(true, face, order)
}