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

    private val generator: MeshGenerator = SquareMeshGenerator(
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
    fun generateMeshAsync(generator: MeshGenerator = this.generator): SmoothMesh {
        val prototype = Smooth.mesh()
        val future = CompletableFuture.supplyAsync { builder(prototype, generator) }
        return SmoothMesh.loadAsync(future)
    }

    @JvmStatic
    fun generateMesh(generator: MeshGenerator = this.generator): SmoothMesh {
        val prototype = Smooth.mesh()
        return builder(prototype, generator).load()
    }

    @JvmStatic
    fun generateMeshAsync(): SmoothMesh = generateMeshAsync(this.generator)

    @JvmStatic
    fun generateMesh(): SmoothMesh = generateMesh(this.generator)

    private fun builder(prototype: SmoothMeshPrototype, generator: MeshGenerator): SmoothMeshBuilder {
        val builder = SmoothMeshBuilder.create(prototype,
                generator.totalVertexCount,
                generator.totalIndexCount)
        generator.generateVertices(builder.writer)
        generator.generateIndices(builder.writer)
        return builder
    }

}