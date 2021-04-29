package org.saar.core.common.terrain.smooth

import org.joml.Vector2i
import org.saar.core.common.smooth.Smooth
import org.saar.core.common.smooth.SmoothMesh
import org.saar.core.common.smooth.SmoothMeshBuilder
import org.saar.core.common.smooth.SmoothMeshPrototype
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.height.FlatHeightGenerator
import org.saar.maths.utils.Vector3
import java.util.concurrent.CompletableFuture
import kotlin.random.Random

object SmoothTerrain {

    private val generator: MeshGenerator = TriangleMeshGenerator(
        32,
        FlatHeightGenerator(0f),
//            { x, z -> SimplexNoise.noise(x, z) },
        ColourGenerator { x, y, z ->
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
        generator.generateVertices(builder)
        generator.generateIndices(builder)
        return builder
    }

}