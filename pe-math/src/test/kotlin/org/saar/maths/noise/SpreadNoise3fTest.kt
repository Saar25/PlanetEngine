package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class SpreadNoise3fTest {

    @Test
    fun `noise divides all three coordinates by the division`() {
        val noise = SpreadNoise3f({ x, y, z -> x * y * z }, 2f)

        val actual = noise.noise(4f, 2f, 2f)

        val expected = 2f * 1f * 1f
        assertEquals(expected, actual, 0.0001f)
    }
}
