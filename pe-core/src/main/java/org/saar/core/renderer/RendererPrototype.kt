package org.saar.core.renderer

import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Uniform

@Deprecated("Use ShadersLink instead")
interface RendererPrototype<T> : ShadersLink {

    fun vertexAttributes(): Array<String> = arrayOf()

    fun fragmentOutputs(): Array<String> = arrayOf()

    override val vertexAttributes get() = vertexAttributes()

    override val fragmentOutputs get() = fragmentOutputs()

    override val uniforms: List<Uniform> get() = emptyList()

    override val shadersProgram: ShadersProgram

    fun onRenderCycle(context: RenderContext) {
    }

    fun onInstanceDraw(context: RenderContext, model: T) {
    }

    fun doInstanceDraw(context: RenderContext, model: T)
}
