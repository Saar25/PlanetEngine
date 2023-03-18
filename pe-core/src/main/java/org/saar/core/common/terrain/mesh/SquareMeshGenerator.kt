package org.saar.core.common.terrain.mesh

import org.joml.Vector2f
import org.saar.maths.utils.Vector2

class SquareMeshGenerator(private val vertices: Int) : MeshGenerator {

    private val space: Float = 1f / (this.vertices - 1)

    override fun generateVertices(): Collection<Vector2f> {
        val vertices: MutableCollection<Vector2f> = mutableListOf()
        for (x in 0 until this.vertices) {
            val vx = x * this.space - .5f
            for (z in 0 until this.vertices) {
                val vz = z * this.space - .5f
                vertices.add(Vector2.of(vx, vz))
            }
        }
        return vertices
    }

    override fun generateIndices(): Collection<Int> {
        val indexPointers = arrayOf(2, 0, 1, 2, 1, 3)

        return (0 until this.vertices - 1).flatMap { x0 ->
            val x1 = x0 + 1
            (0 until this.vertices - 1).flatMap { z0 ->
                val z1 = z0 + 1
                val current = intArrayOf(
                    x0 + z0 * this.vertices,
                    x1 + z0 * this.vertices,
                    x0 + z1 * this.vertices,
                    x1 + z1 * this.vertices)
                indexPointers.map { current[it] }
            }
        }
    }
}