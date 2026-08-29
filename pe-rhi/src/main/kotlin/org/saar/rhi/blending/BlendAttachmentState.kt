package org.saar.rhi.blending

data class BlendAttachmentState(
    val blendEnable: Boolean? = null,
    val srcColorFactor: BlendFactor? = null,
    val dstColorFactor: BlendFactor? = null,
    val colorBlendOp: BlendOp? = null,
    val srcAlphaFactor: BlendFactor? = null,
    val dstAlphaFactor: BlendFactor? = null,
    val alphaBlendOp: BlendOp? = null,
    val colorWriteMask: Int? = null,
)
