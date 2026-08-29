package org.saar.core.renderer.uniforms

import org.saar.core.util.reflection.FieldsLocator
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer

class UniformPropertiesLocator(any: Any) {

    private val fieldsLocator = FieldsLocator(any)

    fun findUniform(): Collection<UniformContainer> =
        this.fieldsLocator.getFilteredValues(
            UniformContainer::class.java,
            UniformProperty::class.java
        )
}
