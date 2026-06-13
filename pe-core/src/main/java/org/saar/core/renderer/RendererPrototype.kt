package org.saar.core.renderer

import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Uniform

interface RendererPrototype<T> {

    fun vertexAttributes(): Array<String> = arrayOf()

    fun fragmentOutputs(): Array<String> = arrayOf()

    val uniforms: List<Uniform> get() = emptyList()

    val shadersProgram: ShadersProgram

    fun onRenderCycle(context: RenderContext) {
    }

    fun onInstanceDraw(context: RenderContext, model: T) {
    }

    fun doInstanceDraw(context: RenderContext, model: T)
}
