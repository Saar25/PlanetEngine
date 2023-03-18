package org.saar.core.common.terrain.mesh

import org.joml.Vector2f

class TriangleMeshGenerator(private val vertices: Int) : MeshGenerator {

    private val space: Float = 1f / (this.vertices - 1)

    override fun generateVertices(): Collection<Vector2f> {
        return (0 until this.vertices).flatMap { x ->
            val vx = x * this.space - .5f
            (0 until this.vertices).map { z ->
                val vz = z * this.space - .5f
                Vector2f(vx, vz)
            }
        }
    }

    override fun generateIndices(): Collection<Int> {
        val indexPointersMap = arrayOf(
            intArrayOf(0, 3, 2, 1, 3, 0),
            intArrayOf(0, 1, 2, 1, 3, 2)
        )

        val indices: MutableCollection<Int> = mutableListOf()
        for (x0 in 0 until this.vertices - 1) {
            val x1 = x0 + 1
            for (z0 in 0 until this.vertices - 1) {
                val z1 = z0 + 1
                val current = intArrayOf(
                    x0 + z0 * this.vertices,
                    x1 + z0 * this.vertices,
                    x0 + z1 * this.vertices,
                    x1 + z1 * this.vertices)

                val indexPointers = indexPointersMap[(x0 + z0) % 2]
                indices.addAll(indexPointers.map { current[it] })
            }
        }
        return indices
    }
}