package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class NoiseUtilsTest {

    @Test
    fun `layered noise1f produces the expected layered value`() {
        val noise: Noise1f = { x -> x }

        val actual = noise.layered(2).noise(3f)

        val expected = (3f + 3f) / 3f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `layered noise2f produces the expected layered value`() {
        val noise: Noise2f = { x, _ -> x }

        val actual = noise.layered(2).noise(3f, 0f)

        val expected = (3f + 3f) / 3f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `layered noise3f produces the expected layered value`() {
        val noise: Noise3f = { x, _, _ -> x }

        val actual = noise.layered(2).noise(3f, 0f, 0f)

        val expected = (3f + 3f) / 3f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `multiplied noise1f scales the value`() {
        val noise: Noise1f = { x -> x }

        val actual = noise.multiplied(3f).noise(4f)

        val expected = 12f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `multiplied noise2f scales the value`() {
        val noise: Noise2f = { x, _ -> x }

        val actual = noise.multiplied(3f).noise(4f, 0f)

        val expected = 12f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `multiplied noise3f scales the value`() {
        val noise: Noise3f = { x, _, _ -> x }

        val actual = noise.multiplied(3f).noise(4f, 0f, 0f)

        val expected = 12f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `offset noise1f shifts the value`() {
        val noise: Noise1f = { x -> x }

        val actual = noise.offset(5f).noise(2f)

        val expected = 7f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `offset noise2f shifts the value`() {
        val noise: Noise2f = { x, _ -> x }

        val actual = noise.offset(5f).noise(2f, 0f)

        val expected = 7f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `offset noise3f shifts the value`() {
        val noise: Noise3f = { x, _, _ -> x }

        val actual = noise.offset(5f).noise(2f, 0f, 0f)

        val expected = 7f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `spread noise1f divides the coordinate`() {
        val noise: Noise1f = { x -> x }

        val actual = noise.spread(2f).noise(6f)

        val expected = 3f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `spread noise2f divides both coordinates`() {
        val noise: Noise2f = { x, y -> x * y }

        val actual = noise.spread(2f).noise(4f, 2f)

        val expected = 2f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `spread noise3f divides all coordinates`() {
        val noise: Noise3f = { x, y, z -> x * y * z }

        val actual = noise.spread(2f).noise(4f, 2f, 2f)

        val expected = 2f
        assertEquals(expected, actual, 0.0001f)
    }
}
