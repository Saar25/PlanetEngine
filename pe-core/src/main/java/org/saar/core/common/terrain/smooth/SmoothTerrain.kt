package org.saar.core.common.terrain.smooth

import org.joml.SimplexNoise
import org.joml.Vector2i
import org.saar.core.common.smooth.Smooth
import org.saar.core.common.smooth.SmoothMesh
import org.saar.core.common.smooth.SmoothMeshBuilder
import org.saar.core.common.smooth.SmoothMeshPrototype
import org.saar.maths.utils.Vector3
import java.util.concurrent.CompletableFuture
import kotlin.random.Random

object SmoothTerrain {

    private val generator = MeshGenerator(
            1024,
            { x, z -> SimplexNoise.noise(x, z) },
            { x, y, z ->
                Vector3.of(
                        Random.nextFloat(),
                        Random.nextFloat(),
                        Random.nextFloat())
            },
            Vector2i(0, 0)
    )

    @JvmStatic
    fun generateMeshAsync(): SmoothMesh {
        val prototype = Smooth.mesh()
        val future = CompletableFuture.supplyAsync { builder(prototype) }
        return SmoothMesh.loadAsync(future)
    }

    @JvmStatic
    fun generateMesh(): SmoothMesh {
        val prototype = Smooth.mesh()
        return builder(prototype).load()
    }

    private fun builder(prototype: SmoothMeshPrototype): SmoothMeshBuilder {
        val builder = SmoothMeshBuilder.create(prototype,
                this.generator.totalVertexCount,
                this.generator.totalIndexCount)
        this.generator.generateIndices(builder.writer)
        this.generator.generateVertices(builder.writer)
        return builder
    }

}