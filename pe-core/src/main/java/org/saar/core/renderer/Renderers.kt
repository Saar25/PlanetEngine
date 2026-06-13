package org.saar.core.renderer

import org.saar.core.renderer.uniforms.UniformPropertiesLocator
import org.saar.core.screen.ScreenBuilder
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.texture.MutableTexture2D

object Renderers {

    val vertexShaderCode: ShaderCode = ShaderCode.loadSource("/shaders/common/quad/quad.vertex.glsl")

    fun findUniforms(renderer: Any): Collection<UniformContainer> {
        return UniformPropertiesLocator(renderer).findUniform()
    }

    inline fun renderToTexture(
        width: Int,
        height: Int,
        internalFormat: InternalFormat,
        render: () -> Unit
    ): MutableTexture2D {
        return MutableTexture2D.create().also { texture ->
            val fbo = Fbo.create(width, height)
            val screen = ScreenBuilder(fbo)
                .addColorTexture(texture, internalFormat)
                .build()

            screen.setAsDraw()
            render()
            screen.delete()
        }
    }
}
