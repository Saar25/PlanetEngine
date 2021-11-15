package org.saar.core.renderer.renderpass

import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

interface RenderPassBuffers

interface AlbedoBuffer : RenderPassBuffers {
    val albedo: ReadOnlyTexture2D
}

interface DepthBuffer : RenderPassBuffers {
    val depth: ReadOnlyTexture2D
}

interface NormalBuffer : RenderPassBuffers {
    val normal: ReadOnlyTexture2D
}

interface SpecularBuffer : RenderPassBuffers {
    val specular: ReadOnlyTexture2D
}

interface NormalSpecularBuffer : RenderPassBuffers {
    val normalSpecular: ReadOnlyTexture2D
}