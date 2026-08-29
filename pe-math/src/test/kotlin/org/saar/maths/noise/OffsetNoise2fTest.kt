package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class OffsetNoise2fTest {

    @Test
    fun `noise adds the offset to the underlying value`() {
        val noise = OffsetNoise2f({ _, y -> y }, 5f)

        val actual = noise.noise(1f, 2f)

        val expected = 7f
        assertEquals(expected, actual, 0.0001f)
    }
}
