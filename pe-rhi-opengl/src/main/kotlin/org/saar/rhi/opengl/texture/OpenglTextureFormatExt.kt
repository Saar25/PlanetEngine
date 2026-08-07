package org.saar.rhi.opengl.texture

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import org.lwjgl.opengl.GL30
import org.saar.rhi.texture.TextureFormat

val TextureFormat.glValue: Int
    get() = when (this) {
        TextureFormat.R8 -> GL30.GL_R8
        TextureFormat.RG8 -> GL30.GL_RG8
        TextureFormat.RGBA8 -> GL11.GL_RGBA8
        TextureFormat.BGRA8 -> GL30.GL_RGBA8
        TextureFormat.RGBA16F -> GL30.GL_RGBA16F
        TextureFormat.RGBA32F -> GL30.GL_RGBA32F
        TextureFormat.DEPTH16 -> GL14.GL_DEPTH_COMPONENT16
        TextureFormat.DEPTH24 -> GL30.GL_DEPTH_COMPONENT24
        TextureFormat.DEPTH32F -> GL30.GL_DEPTH_COMPONENT32F
        TextureFormat.DEPTH24_STENCIL8 -> GL30.GL_DEPTH24_STENCIL8
        TextureFormat.DEPTH32F_STENCIL8 -> GL30.GL_DEPTH32F_STENCIL8
    }
