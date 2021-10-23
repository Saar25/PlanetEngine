package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector2i
import org.joml.Vector2ic
import org.saar.maths.JomlDelegates

class Vec2iUniformValue(
    override val name: String,
    initialValue: Vector2ic = Vector2i(0, 0)
) : Vec2iUniform() {
    override var value: Vector2ic by JomlDelegates.CachedVector2i(Vector2i(initialValue))
}