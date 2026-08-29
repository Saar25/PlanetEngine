package org.saar.maths.utils

import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3f
import kotlin.test.Test
import kotlin.test.assertEquals

class QuaternionTest {

    private fun assertQuaternionClose(expected: Quaternionfc, actual: Quaternionfc, delta: Float = 0.0001f) {
        assertEquals(expected.x(), actual.x(), delta)
        assertEquals(expected.y(), actual.y(), delta)
        assertEquals(expected.z(), actual.z(), delta)
        assertEquals(expected.w(), actual.w(), delta)
    }

    @Test
    fun `create returns an identity quaternion`() {
        val actual = Quaternion.create()

        assertQuaternionClose(Quaternionf(0f, 0f, 0f, 1f), actual)
    }

    @Test
    fun `of constructs a quaternion from its components`() {
        val actual = Quaternion.of(1f, 2f, 3f, 4f)

        assertQuaternionClose(Quaternionf(1f, 2f, 3f, 4f), actual, 0f)
    }

    @Test
    fun `of returns a copy of the given quaternion`() {
        val source = Quaternionf(1f, 2f, 3f, 4f)

        val actual = Quaternion.of(source)
        source.set(9f, 9f, 9f, 9f)

        assertQuaternionClose(Quaternionf(1f, 2f, 3f, 4f), actual, 0f)
    }

    @Test
    fun `createDirection returns a unit quaternion`() {
        val direction = Vector3f(0f, 0f, 1f)

        val actual = Quaternion.createDirection(direction)

        val expected = Quaternionf()
        expected.identity()
        expected.lookAlong(direction, Vector3.UP)
        assertQuaternionClose(expected, actual)
    }

    @Test
    fun `add sums the two quaternions`() {
        val a = Quaternionf(1f, 0f, 0f, 0f)
        val b = Quaternionf(0f, 1f, 0f, 0f)

        val actual = Quaternion.add(a, b)

        assertQuaternionClose(Quaternionf(1f, 1f, 0f, 0f), actual)
    }

    @Test
    fun `mul multiplies the two quaternions via hamilton product`() {
        val a = Quaternionf(0f, 0f, 0f, 1f)
        val b = Quaternionf(0f, 0f, 0f, 1f)

        val actual = Quaternion.mul(a, b)

        assertQuaternionClose(Quaternionf(0f, 0f, 0f, 1f), actual)
    }
}
