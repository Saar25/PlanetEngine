package org.saar.maths.transform

import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.maths.utils.Vector3
import kotlin.test.Test
import kotlin.test.assertEquals

class RotationTest {

    private fun givenVector3f(x: Float, y: Float, z: Float): Vector3fc = Vector3f(x, y, z)

    private fun assertVectorClose(expected: Vector3fc, actual: Vector3fc, delta: Float = 0.0001f) {
        assertEquals(expected.x(), actual.x(), delta)
        assertEquals(expected.y(), actual.y(), delta)
        assertEquals(expected.z(), actual.z(), delta)
    }

    @Test
    fun `create returns an identity rotation that looks along the forward axis`() {
        val rotation = Rotation.create()

        assertVectorClose(givenVector3f(0f, 0f, 1f), rotation.direction)
    }

    @Test
    fun `create returns a rotation with zero euler angles`() {
        val rotation = Rotation.create()

        assertVectorClose(givenVector3f(0f, 0f, 0f), rotation.eulerAngles, 0.001f)
    }

    @Test
    fun `fromQuaternion constructs a rotation from the identity quaternion`() {
        val rotation = Rotation.fromQuaternion(0f, 0f, 0f, 1f)

        assertVectorClose(givenVector3f(0f, 0f, 1f), rotation.direction)
    }

    @Test
    fun `fromEulerAngles constructs an identity rotation for zero angles`() {
        val rotation = Rotation.fromEulerAngles(0f, 0f, 0f)

        assertVectorClose(givenVector3f(0f, 0f, 1f), rotation.direction)
    }

    @Test
    fun `lookAlong orients the forward direction toward the given direction`() {
        val rotation = Rotation.create()

        rotation.lookAlong(Vector3.RIGHT)

        assertVectorClose(givenVector3f(1f, 0f, 0f), rotation.direction)
    }

    @Test
    fun `lookAlong towards the left orients the forward direction to the left`() {
        val rotation = Rotation.create()

        rotation.lookAlong(Vector3.LEFT)

        assertVectorClose(givenVector3f(-1f, 0f, 0f), rotation.direction)
    }
}
