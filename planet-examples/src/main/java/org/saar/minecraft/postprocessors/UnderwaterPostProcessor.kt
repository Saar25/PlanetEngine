package org.saar.minecraft.postprocessors

import org.saar.core.camera.ICamera
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.Screen
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.minecraft.Blocks
import org.saar.minecraft.World
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.GlslVersion
import org.saar.rhi.shader.ShaderModule
import org.saar.rhi.shader.ShaderModuleLoader
import org.saar.rhi.shader.ShaderProgram
import org.saar.rhi.shader.ShaderStage
import org.saar.rhi.shader.ShaderStageType

class UnderwaterPostProcessor(
    private val screen: Screen?,
    private val world: World,
    private val camera: ICamera,
    private val albedoBuffer: ReadOnlyTexture2D,
) : RenderPass {

    private val shadersLink = UnderwaterShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override fun render(context: RenderContext) {
        if (world.getBlock(this.camera.transform.position) !== Blocks.WATER) return

        this.screen?.setAsDraw()
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.textureUniform.value = this.albedoBuffer
        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object UnderwaterShadersLink : ShadersLink {

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + Renderers.quadVertexSource)
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/minecraft/shaders/underwater.pass.glsl"))
            ),
        ).toOpengl()
    }
}
