package org.saar.core.renderer.renderpass

import org.saar.lwjgl.opengl.texture.ReadOnlyTexture

interface RenderPassBuffers

interface AlbedoBuffer : RenderPassBuffers {
    val albedo: ReadOnlyTexture
}

interface DepthBuffer : RenderPassBuffers {
    val depth: ReadOnlyTexture
}

interface NormalBuffer : RenderPassBuffers {
    val normal: ReadOnlyTexture
}

interface SpecularBuffer : RenderPassBuffers {
    val specular: ReadOnlyTexture
}