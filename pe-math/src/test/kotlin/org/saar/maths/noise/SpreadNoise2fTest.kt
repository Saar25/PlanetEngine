package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class SpreadNoise2fTest {

    @Test
    fun `noise divides both coordinates by the division`() {
        val noise = SpreadNoise2f({ x, y -> x * y }, 2f)

        val actual = noise.noise(4f, 2f)

        val expected = 2f * 1f
        assertEquals(expected, actual, 0.0001f)
    }
}
