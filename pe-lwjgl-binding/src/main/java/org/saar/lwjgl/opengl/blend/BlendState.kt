package org.saar.lwjgl.opengl.blend

data class BlendState(
    val enabled: Boolean,
    val function: BlendFunction,
) {
    constructor(function: BlendFunction) : this(true, function)
}