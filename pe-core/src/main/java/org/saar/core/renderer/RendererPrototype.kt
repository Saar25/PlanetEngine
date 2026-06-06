package org.saar.core.renderer

import org.saar.lwjgl.opengl.shader.Shader

interface RendererPrototype<T> {
    fun vertexAttributes(): Array<String> = arrayOf()

    fun fragmentOutputs(): Array<String> = arrayOf()

    val shaders: Array<Shader> get() = emptyArray()

    fun onRenderCycle(context: RenderContext) {
    }

    fun onInstanceDraw(context: RenderContext, model: T) {
    }

    fun doInstanceDraw(context: RenderContext, model: T)
}
