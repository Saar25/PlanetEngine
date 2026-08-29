package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class SpreadNoise1fTest {

    @Test
    fun `noise divides the coordinate by the division`() {
        val noise = SpreadNoise1f({ x -> x }, 2f)

        val actual = noise.noise(6f)

        val expected = 3f
        assertEquals(expected, actual, 0.0001f)
    }
}
