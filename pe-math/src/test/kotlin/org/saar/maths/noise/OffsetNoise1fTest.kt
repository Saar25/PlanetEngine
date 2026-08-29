package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class OffsetNoise1fTest {

    @Test
    fun `noise adds the offset to the underlying value`() {
        val noise = OffsetNoise1f({ x -> x }, 5f)

        val actual = noise.noise(2f)

        val expected = 7f
        assertEquals(expected, actual, 0.0001f)
    }
}
