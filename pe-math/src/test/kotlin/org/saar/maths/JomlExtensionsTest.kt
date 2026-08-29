package org.saar.maths

import org.joml.primitives.Rectanglei
import kotlin.test.Test
import kotlin.test.assertEquals

class JomlExtensionsTest {

    private fun givenRectangle(): Rectanglei = Rectanglei(2, 3, 10, 15)

    @Test
    fun `width returns the horizontal length`() {
        val actual = givenRectangle().width

        val expected = 8
        assertEquals(expected, actual)
    }

    @Test
    fun `height returns the vertical length`() {
        val actual = givenRectangle().height

        val expected = 12
        assertEquals(expected, actual)
    }

    @Test
    fun `offset shifts the rectangle while preserving its size`() {
        val actual = givenRectangle().offset(1, 2)

        assertEquals(3, actual.minX)
        assertEquals(5, actual.minY)
        assertEquals(11, actual.maxX)
        assertEquals(17, actual.maxY)
    }

    @Test
    fun `offset floats produces a float rectangle shifted by the amounts`() {
        val actual = givenRectangle().offset(1.5f, 2.5f)

        assertEquals(3.5f, actual.minX, 0f)
        assertEquals(5.5f, actual.minY, 0f)
        assertEquals(11.5f, actual.maxX, 0f)
        assertEquals(17.5f, actual.maxY, 0f)
    }

    @Test
    fun `toVector4i encodes min and size`() {
        val actual = givenRectangle().toVector4i()

        assertEquals(2, actual.x())
        assertEquals(3, actual.y())
        assertEquals(8, actual.z())
        assertEquals(12, actual.w())
    }

    @Test
    fun `toVector4f encodes min and size as floats`() {
        val actual = givenRectangle().toVector4f()

        assertEquals(2f, actual.x(), 0f)
        assertEquals(3f, actual.y(), 0f)
        assertEquals(8f, actual.z(), 0f)
        assertEquals(12f, actual.w(), 0f)
    }
}
