package org.saar.rhi.vulkan.depthstencil

import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VkPipelineDepthStencilStateCreateInfo
import org.lwjgl.vulkan.VkStencilOpState
import org.saar.rhi.depthstencil.CompareOp
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.depthstencil.StencilOp
import org.saar.rhi.depthstencil.StencilOpState

fun DepthStencilState.toVulkan() =
    VkPipelineDepthStencilStateCreateInfo.calloc().apply {
        `sType$Default`()

        depthTestEnable?.let { depthTestEnable(it) }
        depthWriteEnable?.let { depthWriteEnable(it) }
        depthCompareOp?.let { depthCompareOp(it.vkValue) }
        depthBoundsTestEnable?.let { depthBoundsTestEnable(it) }
        stencilTestEnable?.let { stencilTestEnable(it) }

        front?.let { front().fillStencilOpState(it) }
        back?.let { back().fillStencilOpState(it) }

        minDepthBounds?.let { minDepthBounds(it) }
        maxDepthBounds?.let { maxDepthBounds(it) }
    }

private fun VkStencilOpState.fillStencilOpState(src: StencilOpState) {
    src.failOp?.let { failOp(it.vkValue) }
    src.passOp?.let { passOp(it.vkValue) }
    src.depthFailOp?.let { depthFailOp(it.vkValue) }
    src.compareOp?.let { compareOp(it.vkValue) }
    src.compareMask?.let { compareMask(it) }
    src.writeMask?.let { writeMask(it) }
    src.reference?.let { reference(it) }
}

private val CompareOp.vkValue
    get() = when (this) {
        CompareOp.NEVER -> VK10.VK_COMPARE_OP_NEVER
        CompareOp.LESS -> VK10.VK_COMPARE_OP_LESS
        CompareOp.EQUAL -> VK10.VK_COMPARE_OP_EQUAL
        CompareOp.LESS_OR_EQUAL -> VK10.VK_COMPARE_OP_LESS_OR_EQUAL
        CompareOp.GREATER -> VK10.VK_COMPARE_OP_GREATER
        CompareOp.NOT_EQUAL -> VK10.VK_COMPARE_OP_NOT_EQUAL
        CompareOp.GREATER_OR_EQUAL -> VK10.VK_COMPARE_OP_GREATER_OR_EQUAL
        CompareOp.ALWAYS -> VK10.VK_COMPARE_OP_ALWAYS
    }

private val StencilOp.vkValue
    get() = when (this) {
        StencilOp.KEEP -> VK10.VK_STENCIL_OP_KEEP
        StencilOp.ZERO -> VK10.VK_STENCIL_OP_ZERO
        StencilOp.REPLACE -> VK10.VK_STENCIL_OP_REPLACE
        StencilOp.INCREMENT_AND_CLAMP -> VK10.VK_STENCIL_OP_INCREMENT_AND_CLAMP
        StencilOp.DECREMENT_AND_CLAMP -> VK10.VK_STENCIL_OP_DECREMENT_AND_CLAMP
        StencilOp.INVERT -> VK10.VK_STENCIL_OP_INVERT
        StencilOp.INCREMENT_AND_WRAP -> VK10.VK_STENCIL_OP_INCREMENT_AND_WRAP
        StencilOp.DECREMENT_AND_WRAP -> VK10.VK_STENCIL_OP_DECREMENT_AND_WRAP
    }
