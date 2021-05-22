package org.saar.core.light

import org.joml.Matrix4f
import org.saar.core.camera.ICamera
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue
import org.saar.maths.utils.Vector3
import org.saar.maths.utils.Vector4

abstract class ViewSpaceDirectionalLightUniform(name: String) : Uniform {

    var camera: ICamera? = null

    private val directionUniform = Vec3UniformValue("$name.direction")

    private val colourUniform = Vec3UniformValue("$name.colour")

    final override fun initialize(shadersProgram: ShadersProgram) {
        this.directionUniform.initialize(shadersProgram)
        this.colourUniform.initialize(shadersProgram)
    }

    final override fun load() {
        val value = getUniformValue()
        this.colourUniform.value = value.colour

        if (this.camera != null) {
            val vs = Vector4.of(value.direction, 0f).mul(this.camera!!.viewMatrix.invert(Matrix4f()).transpose())
            this.directionUniform.value = Vector3.of(vs.x(), vs.y(), vs.z()).normalize()
        } else {
            this.directionUniform.value = value.direction.normalize()
        }

        this.colourUniform.load()
        this.directionUniform.load()
    }

    abstract fun getUniformValue(): DirectionalLight
}