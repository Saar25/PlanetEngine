package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class MultipliedNoise1fTest {

    @Test
    fun `noise multiplies the underlying value by the multiplier`() {
        val noise = MultipliedNoise1f({ x -> x }, 3f)

        val actual = noise.noise(4f)

        val expected = 12f
        assertEquals(expected, actual, 0.0001f)
    }
}
