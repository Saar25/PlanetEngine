package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class LayeredNoise2fTest {

    @Test
    fun `noise returns the underlying value when there is a single layer`() {
        val noise = LayeredNoise2f({ _, y -> y }, 1)

        val actual = noise.noise(1f, 3f)

        val expected = 3f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `noise averages the octaves weighted by their frequency`() {
        val noise = LayeredNoise2f({ x, y -> x + y }, 2)

        val actual = noise.noise(3f, 0f)

        val expected = (3f + 3f) / 3f
        assertEquals(expected, actual, 0.0001f)
    }
}
