package org.saar.lwjgl.opengl.depth

import org.saar.lwjgl.opengl.constants.Comparator

data class DepthState(
    val enabled: Boolean,
    val function: DepthFunction,
    val mask: DepthMask,
) {
    constructor(function: DepthFunction, mask: DepthMask) : this(true, function, mask)

    companion object {
        val DISABLED = DepthState(false, DepthFunction(Comparator.ALWAYS), DepthMask.READ)

        val WRITE = DepthState(DepthFunction(Comparator.LESS), DepthMask.WRITE)

        val READ = DepthState(DepthFunction(Comparator.LESS), DepthMask.READ)
    }
}