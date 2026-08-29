package org.saar.maths.transform

import org.joml.Vector3f
import org.joml.Vector3fc
import kotlin.test.Test
import kotlin.test.assertEquals

class PositionTest {

    private fun givenVector3f(x: Float, y: Float, z: Float): Vector3fc = Vector3f(x, y, z)

    private fun assertVectorClose(expected: Vector3fc, actual: Vector3fc, delta: Float = 0.0001f) {
        assertEquals(expected.x(), actual.x(), delta)
        assertEquals(expected.y(), actual.y(), delta)
        assertEquals(expected.z(), actual.z(), delta)
    }

    @Test
    fun `create returns a position at the origin`() {
        val actual = Position.create()

        assertVectorClose(givenVector3f(0f, 0f, 0f), actual.value, 0f)
    }

    @Test
    fun `of constructs a position from components`() {
        val actual = Position.of(1f, 2f, 3f)

        assertVectorClose(givenVector3f(1f, 2f, 3f), actual.value, 0f)
    }

    @Test
    fun `add adds the vector to the current value`() {
        val position = Position.of(1f, 2f, 3f)

        position.add(givenVector3f(1f, 1f, 1f))

        assertVectorClose(givenVector3f(2f, 3f, 4f), position.value)
    }

    @Test
    fun `add adds the components to the current value`() {
        val position = Position.of(1f, 2f, 3f)

        position.add(1f, 2f, 3f)

        assertVectorClose(givenVector3f(2f, 4f, 6f), position.value)
    }

    @Test
    fun `sub subtracts the vector from the current value`() {
        val position = Position.of(5f, 5f, 5f)

        position.sub(givenVector3f(1f, 2f, 3f))

        assertVectorClose(givenVector3f(4f, 3f, 2f), position.value)
    }

    @Test
    fun `set overrides the value with the given vector`() {
        val position = Position.of(1f, 1f, 1f)

        position.set(givenVector3f(9f, 8f, 7f))

        assertVectorClose(givenVector3f(9f, 8f, 7f), position.value, 0f)
    }

    @Test
    fun `individual x y and z properties are readable`() {
        val position = Position.of(1f, 2f, 3f)

        assertEquals(1f, position.x, 0f)
        assertEquals(2f, position.y, 0f)
        assertEquals(3f, position.z, 0f)
    }

    @Test
    fun `setting x y and z properties updates the value`() {
        val position = Position.of(1f, 2f, 3f)

        position.x = 4f
        position.y = 5f
        position.z = 6f

        assertVectorClose(givenVector3f(4f, 5f, 6f), position.value, 0f)
    }

    @Test
    fun `addX addY and addZ update the individual components`() {
        val position = Position.of(1f, 2f, 3f)

        position.addX(1f)
        position.addY(2f)
        position.addZ(3f)

        assertVectorClose(givenVector3f(2f, 4f, 6f), position.value, 0f)
    }

    @Test
    fun `subX subY and subZ update the individual components`() {
        val position = Position.of(5f, 5f, 5f)

        position.subX(1f)
        position.subY(2f)
        position.subZ(3f)

        assertVectorClose(givenVector3f(4f, 3f, 2f), position.value, 0f)
    }
}
