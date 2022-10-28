package org.saar.lwjgl.opengl.depth

data class DepthState(
    val enabled: Boolean,
    val function: DepthFunction,
    val mask: DepthMask,
) {
    constructor(function: DepthFunction, mask: DepthMask) : this(true, function, mask)
}