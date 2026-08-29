package org.saar.rhi.blending

fun BlendState(
    attachment: BlendAttachmentState,
    logicOpEnable: Boolean? = null,
    logicOp: LogicOp? = null,
    blendConstants: BlendConstants? = null,
) = BlendState(
    attachments = listOf(attachment),
    logicOpEnable = logicOpEnable,
    logicOp = logicOp,
    blendConstants = blendConstants,
)

data class BlendState(
    val attachments: List<BlendAttachmentState> = emptyList(),
    val logicOpEnable: Boolean? = null,
    val logicOp: LogicOp? = null,
    val blendConstants: BlendConstants? = null,
) {
    companion object {
        val ALPHA = BlendState(
            attachment = BlendAttachmentState(
                blendEnable = true,
                srcColorFactor = BlendFactor.SRC_ALPHA,
                dstColorFactor = BlendFactor.ONE_MINUS_SRC_ALPHA,
            ),
        )
    }
}