package org.saar.rhi.vulkan.shader

import org.lwjgl.vulkan.VkAllocationCallbacks
import org.lwjgl.vulkan.VkDevice
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo
import org.saar.rhi.shader.ShaderProgram

fun ShaderProgram.toVulkan(
    device: VkDevice,
    allocator: VkAllocationCallbacks? = null
): VkPipelineShaderStageCreateInfo.Buffer {
    val stages = VkPipelineShaderStageCreateInfo.calloc(this.stages.size)
    for ((i, s) in this.stages.withIndex()) {
        stages[i].set(s.toVulkan(device, allocator))
    }
    return stages
}