package org.saar.core.renderer

import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer

interface ShadersLink {

    val vertexAttributes: Array<out String> get() = emptyArray()

    val fragmentOutputs: Array<out String> get() = emptyArray()

    val uniforms: Iterable<UniformContainer> get() = emptyList()

    val shadersProgram: ShadersProgram

}

fun ShadersLink.init() {
    this.shadersProgram.bind()
    this.shadersProgram.bindAttributes(*this.vertexAttributes)
    this.shadersProgram.bindFragmentOutputs(*this.fragmentOutputs)
}