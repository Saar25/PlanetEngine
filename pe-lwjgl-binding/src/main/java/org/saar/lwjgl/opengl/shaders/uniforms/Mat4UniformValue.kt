package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Matrix4f
import org.saar.maths.JomlDelegates
import org.saar.maths.utils.Matrix4

class Mat4UniformValue(
    override val name: String,
    initialValue: Matrix4f = Matrix4.create(),
    override val transpose: Boolean = false
) : Mat4Uniform() {
    override var value: Matrix4f by JomlDelegates.CachedMatrix4f(Matrix4.of(initialValue))
}