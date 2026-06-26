package org.saar.core.renderer

import org.saar.core.renderer.uniforms.UniformPropertiesLocator
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.texture.MutableTexture2D

object Renderers {

    val quadVertexShaderCode: ShaderCode = ShaderCode.loadSource("/shaders/common/quad/quad.vertex.glsl")

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
            val fbo = Fbo.create()
            val screen = buildScreen(fbo, width, height) {
                colorAttachment(texture, internalFormat)
            }

            screen.setAsDraw()
            render()
            fbo.delete()
        }
    }
}
