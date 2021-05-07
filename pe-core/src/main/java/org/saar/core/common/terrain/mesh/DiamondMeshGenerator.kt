package org.saar.core.common.terrain.mesh

import org.joml.Vector2f
import org.saar.maths.utils.Vector2
import kotlin.math.min

class DiamondMeshGenerator(private val vertices: Int) : MeshGenerator {

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
        for (x in 0 until this.vertices - 1) {
            val vx = (x + .5f) * this.space - .5f
            for (z in 0 until this.vertices - 1) {
                val vz = (z + .5f) * this.space - .5f
                vertices.add(Vector2.of(vx, vz))
            }
        }
        return vertices
    }

    override fun generateIndices(): Collection<Int> {
        val indices: MutableCollection<Int> = mutableListOf()

        val innerOffset = this.vertices * this.vertices
        for (x1 in 0 until this.vertices - 1) {
            val x2 = x1 + min(this.vertices - x1 - 1, 1)
            for (z1 in 0 until this.vertices - 1) {
                val z2 = z1 + min(this.vertices - z1 - 1, 1)

                val current = intArrayOf(
                    x1 + z1 * this.vertices,
                    x2 + z1 * this.vertices,
                    x2 + z2 * this.vertices,
                    x1 + z2 * this.vertices
                )
                val center = innerOffset + x1 + z1 * (this.vertices - 1)
                for (i in 0..3) {
                    indices.add(current[(i + 1) % 4])
                    indices.add(center)
                    indices.add(current[i])
                }
            }
        }
        return indices
    }
}