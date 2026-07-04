package org.saar.rhi.blending

data class BlendState(
    val attachments: List<BlendAttachmentState> = emptyList(),
    val logicOpEnable: Boolean? = null,
    val logicOp: LogicOp? = null,
    val blendConstants: BlendConstants? = null,
)
