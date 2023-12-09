package org.saar.lwjgl.opengl.blend

data class BlendState(
    val enabled: Boolean = true,
    val function: BlendFunction,
) {
    constructor(function: BlendFunction) : this(true, function)

    companion object {
        val ALPHA = BlendState(
            function = BlendFunction(BlendValue.SRC_ALPHA, BlendValue.ONE_MINUS_SRC_ALPHA),
        )

        val ADDITIVE = BlendState(
            function = BlendFunction(BlendValue.SRC_ALPHA, BlendValue.ONE),
        )

        val DEFAULTS = BlendState(
            enabled = false,
            function = BlendFunction(BlendValue.ONE, BlendValue.ZERO),
        )
    }
}