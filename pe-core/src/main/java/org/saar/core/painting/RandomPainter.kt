package org.saar.core.painting

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.ShadersLink
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram

class RandomPainter : RenderPass {

    private val shadersLink = RandomPainterPrototype

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()

        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object RandomPainterPrototype : ShadersLink {

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/painting/random.fragment.glsl")),
        )
    }
}