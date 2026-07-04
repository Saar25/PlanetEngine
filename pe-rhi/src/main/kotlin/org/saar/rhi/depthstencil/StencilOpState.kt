package org.saar.rhi.depthstencil

data class StencilOpState(
    val failOp: StencilOp? = null,
    val passOp: StencilOp? = null,
    val depthFailOp: StencilOp? = null,
    val compareOp: CompareOp? = null,
    val compareMask: Int? = null,
    val writeMask: Int? = null,
    val reference: Int? = null,
) {
    companion object {
        val ALWAYS_WRITE = StencilOpState(
            failOp = StencilOp.KEEP,
            depthFailOp = StencilOp.KEEP,
            passOp = StencilOp.REPLACE,
            compareOp = CompareOp.ALWAYS,
            reference = 1,
            compareMask = 0xFF,
            writeMask = -1,
        )

        val UNWRITTEN_ONLY = StencilOpState(
            failOp = StencilOp.KEEP,
            depthFailOp = StencilOp.KEEP,
            passOp = StencilOp.REPLACE,
            compareOp = CompareOp.EQUAL,
            reference = 0,
            compareMask = -1,
            writeMask = -1,
        )

        val REPLACE = StencilOpState(
            failOp = StencilOp.KEEP,
            depthFailOp = StencilOp.KEEP,
            passOp = StencilOp.REPLACE,
            compareOp = CompareOp.NOT_EQUAL,
            reference = 0,
            compareMask = -1,
            writeMask = -1,
        )
    }
}