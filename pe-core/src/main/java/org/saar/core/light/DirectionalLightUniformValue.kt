package org.saar.core.light

import org.saar.lwjgl.opengl.shaders.uniforms2.UniformValue

class DirectionalLightUniformValue(name: String) : DirectionalLightUniform(name), UniformValue<IDirectionalLight> {

    private val value: DirectionalLight = DirectionalLight()

    override fun setValue(value: IDirectionalLight) {
        this.value.colour.set(value.colour)
        this.value.direction.set(value.direction)
    }

    override fun getValue(): DirectionalLight {
        return this.value
    }

    override fun getUniformValue(): DirectionalLight {
        return this.value
    }
}