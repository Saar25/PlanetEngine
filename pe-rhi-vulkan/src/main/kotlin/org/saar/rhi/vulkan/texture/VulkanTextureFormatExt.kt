package org.saar.rhi.vulkan.texture

import org.lwjgl.vulkan.VK10
import org.saar.rhi.renderpass.TextureFormat

val TextureFormat.vkValue: Int
    get() = when (this) {
        TextureFormat.R8 -> VK10.VK_FORMAT_R8_UNORM
        TextureFormat.RG8 -> VK10.VK_FORMAT_R8G8_UNORM
        TextureFormat.RGBA8 -> VK10.VK_FORMAT_R8G8B8A8_UNORM
        TextureFormat.BGRA8 -> VK10.VK_FORMAT_B8G8R8A8_UNORM
        TextureFormat.RGBA16F -> VK10.VK_FORMAT_R16G16B16A16_SFLOAT
        TextureFormat.RGBA32F -> VK10.VK_FORMAT_R32G32B32A32_SFLOAT
        TextureFormat.DEPTH16 -> VK10.VK_FORMAT_D16_UNORM
        TextureFormat.DEPTH24 -> VK10.VK_FORMAT_D24_UNORM_S8_UINT
        TextureFormat.DEPTH32F -> VK10.VK_FORMAT_D32_SFLOAT
        TextureFormat.DEPTH24_STENCIL8 -> VK10.VK_FORMAT_D24_UNORM_S8_UINT
        TextureFormat.DEPTH32F_STENCIL8 -> VK10.VK_FORMAT_D32_SFLOAT_S8_UINT
    }
