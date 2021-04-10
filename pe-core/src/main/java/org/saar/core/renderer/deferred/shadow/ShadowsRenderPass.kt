package org.saar.core.renderer.deferred.shadow

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.renderer.*
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassBase
import org.saar.core.renderer.deferred.RenderPassContext
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.*
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.*
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.maths.utils.Matrix4

class ShadowsRenderPass(private val shadowCamera: ICamera,
                        private val shadowMap: ReadOnlyTexture,
                        private val light: DirectionalLight)
    : RenderPassBase(), RenderPass {

    private var uniformsHelper = UniformsHelper.empty()
    private var instanceUpdatersHelper = UpdatersHelper.empty<DeferredRenderingBuffers>()

    @UniformProperty
    private val shadowMatrixUniform = object : Mat4Uniform() {
        override fun getName(): String = "shadowMatrix"

        override fun getUniformValue(): Matrix4fc {
            return this@ShadowsRenderPass.shadowCamera.projection.matrix.mul(
                this@ShadowsRenderPass.shadowCamera.viewMatrix, matrix)
        }
    }

    @UniformProperty
    private val projectionMatrixInvUniform = Mat4UniformValue("projectionMatrixInv")

    @UniformProperty
    private val viewMatrixInvUniform = Mat4UniformValue("viewMatrixInv")

    @UniformProperty
    private val cameraWorldPositionUniform = Vec3UniformValue("cameraWorldPosition")

    @UniformProperty
    private val pcfRadiusUniform = object : IntUniform() {
        override fun getName(): String = "pcfRadius"

        override fun getUniformValue(): Int = 2
    }

    @UniformProperty
    private val lightUniform = object : DirectionalLightUniform("light") {
        override fun getUniformValue(): DirectionalLight {
            return this@ShadowsRenderPass.light
        }
    }

    @UniformProperty
    private val shadowMapUniform = object : TextureUniform() {
        override fun getUnit(): Int = 0

        override fun getName(): String = "shadowMap"

        override fun getUniformValue(): ReadOnlyTexture {
            return this@ShadowsRenderPass.shadowMap
        }
    }

    @UniformProperty
    private val colourTextureUniform = TextureUniformValue("colourTexture", 1)

    @UniformProperty
    private val normalTextureUniform = TextureUniformValue("normalTexture", 2)

    @UniformProperty
    private val depthTextureUniform = TextureUniformValue("depthTexture", 3)

    @UniformUpdaterProperty
    private val colourTextureUpdater = UniformUpdater<DeferredRenderingBuffers> { state ->
        this@ShadowsRenderPass.colourTextureUniform.value = state.instance.albedo
    }

    @UniformUpdaterProperty
    private val normalTextureUpdater = UniformUpdater<DeferredRenderingBuffers> { state ->
        this@ShadowsRenderPass.normalTextureUniform.value = state.instance.normal
    }

    @UniformUpdaterProperty
    private val depthTextureUpdater = UniformUpdater<DeferredRenderingBuffers> { state ->
        this@ShadowsRenderPass.depthTextureUniform.value = state.instance.depth
    }

    @ShaderProperty(ShaderType.VERTEX)
    private val vertexShader: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/deferred/quadVertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),
        ShaderCode.loadSource("/shaders/common/transform/transform.header.glsl"),
        ShaderCode.loadSource("/shaders/common/light/light.struct.glsl"),
        ShaderCode.loadSource("/shaders/common/light/light.header.glsl"),
        ShaderCode.loadSource("/shaders/deferred/shadow/fragment.glsl"),
        ShaderCode.loadSource("/shaders/common/light/light.source.glsl"),
        ShaderCode.loadSource("/shaders/common/transform/transform.source.glsl"))

    init {
        buildShadersProgram()
        bindFragmentOutputs("f_colour")

        this.uniformsHelper = buildHelper(uniformsHelper)
        this.instanceUpdatersHelper = buildHelper(instanceUpdatersHelper)
    }

    companion object {
        private val matrix: Matrix4f = Matrix4.create()
    }

    override fun onRender(context: RenderPassContext) {
        val instance = RenderState(context.buffers)
        this.instanceUpdatersHelper.update(instance)

        this.projectionMatrixInvUniform.value = context.camera.projection.matrix.invertPerspective(matrix)
        this.cameraWorldPositionUniform.value = context.camera.transform.position.value
        this.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(matrix)

        this.uniformsHelper.load()

        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }
}