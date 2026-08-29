package org.saar.core.renderer

import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.rhi.opengl.shader.OpenglShaderProgram
import org.saar.rhi.opengl.shader.bindAttributes
import org.saar.rhi.opengl.shader.bindFragmentOutputs

interface ShadersLink {

    val vertexAttributes: Array<out String> get() = emptyArray()

    val fragmentOutputs: Array<out String> get() = emptyArray()

    val uniforms: Iterable<UniformContainer> get() = emptyList()

    val shadersProgram: OpenglShaderProgram

}

fun ShadersLink.init() {
    this.shadersProgram.bind()
    this.shadersProgram.bindAttributes(*this.vertexAttributes)
    this.shadersProgram.bindFragmentOutputs(*this.fragmentOutputs)
}