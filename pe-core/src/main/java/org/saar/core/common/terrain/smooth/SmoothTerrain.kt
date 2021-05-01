package org.saar.core.common.terrain.smooth

import org.joml.Vector2fc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.core.common.smooth.*
import org.saar.core.common.smooth.Smooth.vertex
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.height.FlatHeightGenerator
import org.saar.core.common.terrain.height.HeightGenerator
import org.saar.core.common.terrain.mesh.MeshGenerator
import org.saar.core.common.terrain.mesh.TriangleMeshGenerator
import org.saar.maths.utils.Maths
import org.saar.maths.utils.Vector3
import java.util.concurrent.CompletableFuture
import kotlin.random.Random

object SmoothTerrain {

    private val heightGenerator: HeightGenerator = FlatHeightGenerator(0f)

    private val colourGenerator: ColourGenerator = ColourGenerator { x, y, z ->
        Vector3.of(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
    }

    private val meshGenerator: MeshGenerator = TriangleMeshGenerator(32)

    @JvmStatic
    @JvmOverloads
    fun generateMeshAsync(generator: MeshGenerator = this.meshGenerator): SmoothMesh {
        val prototype = Smooth.mesh()
        val future = CompletableFuture.supplyAsync { builder(prototype, generator) }
        return SmoothMesh.loadAsync(future)
    }

    @JvmStatic
    @JvmOverloads
    fun generateMesh(generator: MeshGenerator = this.meshGenerator): SmoothMesh {
        val prototype = Smooth.mesh()
        return builder(prototype, generator).load()
    }

    private fun builder(prototype: SmoothMeshPrototype, generator: MeshGenerator): SmoothMeshBuilder {
        val builder = SmoothMeshBuilder.create(prototype)
        builder.writeVertices(generator.generateVertices().map { generateVertex(it) })
        builder.writeIndices(generator.generateIndices())
        return builder
    }

    private fun vertexPosition(x: Float, z: Float, dest: Vector3f): Vector3fc {
        return dest.set(x, this.heightGenerator.generateHeight(x, z), z)
    }

    private fun vertexNormal(position: Vector3fc, xOffset: Float, zOffset: Float): Vector3fc {
        val p2 = vertexPosition(position.x(), position.z() + xOffset, Vector3.create())
        val p3 = vertexPosition(position.x() + zOffset, position.z(), Vector3.create())
        return Maths.calculateNormal(position, p2, p3)
    }

    private fun vertexColour(v: Vector3fc): Vector3fc {
        return this.colourGenerator.generateColour(v.x(), v.y(), v.z())
    }

    private fun generateVertex(vertex: Vector2fc): SmoothVertex {
        val position: Vector3fc = vertexPosition(vertex.x(), vertex.y(), Vector3.create())
        return vertex(position,
            vertexNormal(position, .1f, .1f),
            vertexColour(position),
            Vector3.of(0f, Math.random().toFloat() * 0.1f, 0f))
    }
}