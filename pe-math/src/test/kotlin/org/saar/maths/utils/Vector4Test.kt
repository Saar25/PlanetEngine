package org.saar.maths.utils

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import org.joml.Vector4fc
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector4Test {

    private fun assertVectorClose(expected: Vector4fc, actual: Vector4fc, delta: Float = 0f) {
        assertEquals(expected.x(), actual.x(), delta)
        assertEquals(expected.y(), actual.y(), delta)
        assertEquals(expected.z(), actual.z(), delta)
        assertEquals(expected.w(), actual.w(), delta)
    }

    @Test
    fun `create returns a vector with components zero except w equal to one`() {
        val actual = Vector4.create()

        assertVectorClose(Vector4f(0f, 0f, 0f, 1f), actual)
    }

    @Test
    fun `of constructs a vector from its components`() {
        val actual = Vector4.of(1f, 2f, 3f, 4f)

        assertVectorClose(Vector4f(1f, 2f, 3f, 4f), actual)
    }

    @Test
    fun `of constructs a vector with all components set to the same value`() {
        val actual = Vector4.of(4f)

        assertVectorClose(Vector4f(4f, 4f, 4f, 4f), actual)
    }

    @Test
    fun `of returns a copy of the given vector`() {
        val source = Vector4f(1f, 2f, 3f, 4f)

        val actual = Vector4.of(source)
        source.set(9f, 9f, 9f, 9f)

        assertVectorClose(Vector4f(1f, 2f, 3f, 4f), actual)
    }

    @Test
    fun `of combines a vector3 and a w component`() {
        val v = Vector3f(1f, 2f, 3f)

        val actual = Vector4.of(v, 4f)

        assertVectorClose(Vector4f(1f, 2f, 3f, 4f), actual)
    }

    @Test
    fun `of combines a vector2 with z and w components`() {
        val v = Vector2f(1f, 2f)

        val actual = Vector4.of(v, 3f, 4f)

        assertVectorClose(Vector4f(1f, 2f, 3f, 4f), actual)
    }

    @Test
    fun `of combines two vector2 into xy and zw`() {
        val v1 = Vector2f(1f, 2f)
        val v2 = Vector2f(3f, 4f)

        val actual = Vector4.of(v1, v2)

        assertVectorClose(Vector4f(1f, 2f, 3f, 4f), actual)
    }
}
