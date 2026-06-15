package org.saar.core.renderer

import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Uniform

interface ShadersLink {

    val vertexAttributes: Array<String> get() = emptyArray()

    val fragmentOutputs: Array<String> get() = emptyArray()

    val uniforms: List<Uniform> get() = emptyList()

    val shadersProgram: ShadersProgram

}

fun ShadersLink.init() {
    this.shadersProgram.bind()
    this.shadersProgram.bindAttributes(*this.vertexAttributes)
    this.shadersProgram.bindFragmentOutputs(*this.fragmentOutputs)
}