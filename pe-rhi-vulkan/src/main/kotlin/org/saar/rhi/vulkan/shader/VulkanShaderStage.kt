package org.saar.rhi.vulkan.shader

import org.lwjgl.system.MemoryUtil
import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VkAllocationCallbacks
import org.lwjgl.vulkan.VkDevice
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo
import org.saar.rhi.shader.ShaderStage
import org.saar.rhi.shader.ShaderStageType

fun ShaderStage.toVulkan(device: VkDevice, allocator: VkAllocationCallbacks? = null) =
    VkPipelineShaderStageCreateInfo.calloc().apply {
        `sType$Default`()
        stage(this@toVulkan.type.vkValue)
        pName(MemoryUtil.memASCII(this@toVulkan.entryPoint ?: "main"))
        module(this@toVulkan.module.toVulkan(device, allocator))
    }

val ShaderStageType.vkValue
    get() = when (this) {
        ShaderStageType.VERTEX -> VK10.VK_SHADER_STAGE_VERTEX_BIT
        ShaderStageType.TESSELLATION_CONTROL -> VK10.VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT
        ShaderStageType.TESSELLATION_EVALUATION -> VK10.VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT
        ShaderStageType.GEOMETRY -> VK10.VK_SHADER_STAGE_GEOMETRY_BIT
        ShaderStageType.FRAGMENT -> VK10.VK_SHADER_STAGE_FRAGMENT_BIT
        ShaderStageType.COMPUTE -> VK10.VK_SHADER_STAGE_COMPUTE_BIT
    }
