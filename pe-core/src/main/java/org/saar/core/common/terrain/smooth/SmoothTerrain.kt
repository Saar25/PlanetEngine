package org.saar.core.common.terrain.smooth

import org.joml.SimplexNoise
import org.joml.Vector2i
import org.saar.core.common.smooth.SmoothMesh
import org.saar.maths.utils.Vector3
import kotlin.random.Random

object SmoothTerrain {

    @JvmStatic
    fun generateMesh(): SmoothMesh {
        val meshGenerator = MeshGenerator(
                16,
                { x, z -> SimplexNoise.noise(x, z) },
                { x, y, z ->
                    Vector3.of(
                            Random.nextFloat(),
                            Random.nextFloat(),
                            Random.nextFloat())
                },
                Vector2i(0, 0)
        )

        val vertices = meshGenerator.generateVertices()
        val indices = meshGenerator.generateIndices()
        return SmoothMesh.load(vertices, indices)
    }

}