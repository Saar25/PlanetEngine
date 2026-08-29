package org.saar.maths.objects

import kotlin.test.Test
import kotlin.test.assertEquals

class PolygonTest {

    @Test
    fun `new polygon has no vertices`() {
        val polygon = Polygon()

        assertEquals(0, polygon.vertices.size)
    }

    @Test
    fun `addVertex appends the vertex to the polygon`() {
        val polygon = Polygon()

        polygon.addVertex(1f, 2f)
        polygon.addVertex(3f, 4f)

        assertEquals(2, polygon.vertices.size)
        assertEquals(1f, polygon.vertices[0].x, 0f)
        assertEquals(2f, polygon.vertices[0].y, 0f)
        assertEquals(3f, polygon.vertices[1].x, 0f)
        assertEquals(4f, polygon.vertices[1].y, 0f)
    }
}
