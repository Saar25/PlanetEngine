package org.saar.maths.utils

import org.joml.Vector2f
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector2Test {

    @Test
    fun `create returns a zero vector`() {
        val actual = Vector2.create()

        val expected = Vector2f(0f, 0f)
        assertEquals(expected.x, actual.x, 0f)
        assertEquals(expected.y, actual.y, 0f)
    }

    @Test
    fun `of returns a copy of the given vector`() {
        val source = Vector2f(1f, 2f)

        val actual = Vector2.of(source)
        source.set(9f, 9f)

        val expected = Vector2f(1f, 2f)
        assertEquals(expected.x, actual.x, 0f)
        assertEquals(expected.y, actual.y, 0f)
    }

    @Test
    fun `of constructs a vector from its components`() {
        val actual = Vector2.of(3f, 4f)

        val expected = Vector2f(3f, 4f)
        assertEquals(expected.x, actual.x, 0f)
        assertEquals(expected.y, actual.y, 0f)
    }
}
