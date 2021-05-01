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
        val indices: MutableCollection<Int> = mutableListOf()
        for (x0 in 0 until this.vertices - 1) {
            for (z0 in 0 until this.vertices - 1) {
                val x1 = x0 + 1
                val z1 = z0 + 1
                val current = intArrayOf(
                    x0 + z0 * this.vertices,
                    x1 + z0 * this.vertices,
                    x0 + z1 * this.vertices,
                    x1 + z1 * this.vertices)

                indices.add(current[2])
                indices.add(current[0])
                indices.add(current[1])
                indices.add(current[2])
                indices.add(current[1])
                indices.add(current[3])
            }
        }
        return indices
    }
}