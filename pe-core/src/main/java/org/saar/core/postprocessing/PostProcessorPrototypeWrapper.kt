package org.saar.core.postprocessing

import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlRendering

class PostProcessorPrototypeWrapper(private val prototype: PostProcessorPrototype) : PostProcessor {

    companion object {
        private val vertexShaderCode = ShaderCode.loadSource(
            "/shaders/postprocessing/default.vertex.glsl")
    }

    private val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, vertexShaderCode),
        this.prototype.fragmentShader())

    override fun process(image: ReadOnlyTexture) {
        this.shadersProgram.bind()

        this.prototype.onRender()

        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)

        this.shadersProgram.unbind()
    }

    override fun delete() {
        this.shadersProgram.delete()
        this.prototype.onDelete()
    }
}