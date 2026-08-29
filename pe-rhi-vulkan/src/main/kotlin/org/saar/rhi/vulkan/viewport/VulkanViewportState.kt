package org.saar.rhi.vulkan.viewport

import org.lwjgl.vulkan.VkPipelineViewportStateCreateInfo
import org.lwjgl.vulkan.VkRect2D
import org.lwjgl.vulkan.VkViewport
import org.saar.rhi.viewport.ViewportState

// TODO: should support dynamic viewport
fun ViewportState.toVulkan(): VkPipelineViewportStateCreateInfo {
    val viewports =
        if (this.viewports.isEmpty()) null
        else this.viewports.let { list ->
            VkViewport.calloc(list.size).also { buffer ->
                for ((i, viewport) in list.withIndex()) {
                    val (x, y, width, height, minDepth, maxDepth) = viewport
                    buffer[i].also {
                        it.x(x).y(y)
                        it.width(width).height(height)
                        it.minDepth(minDepth ?: 0f).maxDepth(maxDepth ?: 1f)
                    }
                }
            }
        }

    val scissors =
        if (this.scissors.isEmpty()) null
        else this.scissors.let { list ->
            VkRect2D.calloc(list.size).also { buffer ->
                for ((i, scissor) in list.withIndex()) {
                    val (x, y, width, height) = scissor
                    buffer[i].also {
                        it.offset().x(x).y(y)
                        it.extent().width(width).height(height)
                    }
                }
            }
        }

    return VkPipelineViewportStateCreateInfo.calloc().apply {
        `sType$Default`()
        viewports?.let {
            viewportCount(it.capacity())
            pViewports(it)
        }
        scissors?.let {
            scissorCount(it.capacity())
            pScissors(it)
        }
    }
}
