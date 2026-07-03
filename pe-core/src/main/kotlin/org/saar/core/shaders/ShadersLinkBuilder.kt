package org.saar.core.shaders

import org.saar.core.renderer.ShadersLink
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer

class ShadersLinkBuilder {

    var vertexAttributes: Array<out String> = arrayOf()

    var fragmentOutputs: Array<out String> = arrayOf()

    var uniforms = mutableListOf<UniformContainer>()

    lateinit var shadersProgram: ShadersProgram

    fun build(): ShadersLink = object : ShadersLink {
        override val shadersProgram = this@ShadersLinkBuilder.shadersProgram

        override val vertexAttributes = this@ShadersLinkBuilder.vertexAttributes

        override val fragmentOutputs = this@ShadersLinkBuilder.fragmentOutputs

        override val uniforms = this@ShadersLinkBuilder.uniforms
    }
}