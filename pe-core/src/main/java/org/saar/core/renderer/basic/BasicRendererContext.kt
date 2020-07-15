package org.saar.core.renderer.basic

import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.annotations.InstanceUniformProperty
import org.saar.lwjgl.opengl.shaders.RenderState
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.uniforms.UniformVec2Property
import org.saar.maths.utils.Vector2

class BasicRendererContext(shadersProgram: ShadersProgram) : AbstractRenderer(shadersProgram) {

    @InstanceUniformProperty
    private val windowSize: UniformProperty<Any> = object : UniformVec2Property<Any>("windowSize") {
        override fun getUniformValue(state: RenderState<Any>) = Vector2.of(500f, 500f)
    }

    val renderNode: BasicRenderNode = BasicRenderNode()

    init {
        init()
    }

    override fun render() {}
    override fun delete() {}
}