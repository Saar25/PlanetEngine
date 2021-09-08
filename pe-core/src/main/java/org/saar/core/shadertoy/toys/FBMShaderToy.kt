package org.saar.core.shadertoy.toys

import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.shadertoy.ShaderToy
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform

class FBMShaderToy : ShaderToy {

    private val prototype = FBMShaderToyPrototype()
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render() = this.wrapper.render()

    override fun delete() = this.wrapper.delete()
}

private class FBMShaderToyPrototype : RenderPassPrototype {

    private val startTime = System.currentTimeMillis()

    @UniformProperty
    val timeUniform = object : FloatUniform() {
        override fun getUniformValue() = (System.currentTimeMillis() - startTime) / 1000f

        override fun getName() = "u_time"
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/shadertoy/fbm.fragment.glsl")
    )
}