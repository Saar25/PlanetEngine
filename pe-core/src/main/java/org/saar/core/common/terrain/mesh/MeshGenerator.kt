package org.saar.core.common.terrain.mesh

import org.joml.Vector2f

interface MeshGenerator {
    fun generateVertices(): Collection<Vector2f>
    fun generateIndices(): Collection<Int>
}