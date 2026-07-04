package org.saar.rhi.depthstencil

data class StencilOpState(
    val failOp: StencilOp? = null,
    val passOp: StencilOp? = null,
    val depthFailOp: StencilOp? = null,
    val compareOp: CompareOp? = null,
    val compareMask: Int? = null,
    val writeMask: Int? = null,
    val reference: Int? = null,
)
