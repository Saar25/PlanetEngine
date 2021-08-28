package org.saar.core.renderer.renderpass

import org.saar.lwjgl.opengl.shaders.Shader

interface RenderPassPrototype<T : RenderPassBuffers> {

    fun fragmentShader(): Shader

    fun onRender(context: RenderPassContext, buffers: T)

    fun onDelete() {}

}