package org.saar.core.common.terrain.smooth

import org.joml.Vector2fc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.core.common.smooth.*
import org.saar.core.common.smooth.Smooth.vertex
import org.saar.core.common.terrain.Terrain
import org.saar.maths.utils.Maths
import org.saar.maths.utils.Vector3
import java.util.concurrent.CompletableFuture

class SmoothTerrain private constructor(private val configuration: SmoothTerrainConfiguration,
                                        model: SmoothModel) : SmoothNode(model), Terrain {

    companion object {
        @JvmStatic
        fun generate(configuration: SmoothTerrainConfiguration): SmoothTerrain {
            val prototype = Smooth.mesh()
            val mesh = builder(configuration, prototype).load()
            val model = SmoothModel(mesh)
            return SmoothTerrain(configuration, model)
        }

        @JvmStatic
        fun generateAsync(configuration: SmoothTerrainConfiguration): SmoothTerrain {
            val prototype = Smooth.mesh()
            val future = CompletableFuture.supplyAsync {
                builder(configuration, prototype)
            }
            val mesh = SmoothMesh.loadAsync(future)
            val model = SmoothModel(mesh)
            return SmoothTerrain(configuration, model)
        }

    }

    override fun getHeight(x: Float, z: Float): Float {
        return this.configuration.heightGenerator.generateHeight(x, z)
    }
}

private fun builder(configuration: SmoothTerrainConfiguration,
                    prototype: SmoothMeshPrototype): SmoothMeshBuilder {
    val builder = SmoothMeshBuilder.create(prototype)
    builder.writeVertices(configuration.meshGenerator.generateVertices()
        .map { generateVertex(configuration, it) })
    builder.writeIndices(configuration.meshGenerator.generateIndices())
    return builder
}

private fun vertexPosition(configuration: SmoothTerrainConfiguration,
                           x: Float, z: Float, dest: Vector3f): Vector3fc {
    return dest.set(x, configuration.heightGenerator.generateHeight(x, z), z)
}

private fun vertexNormal(configuration: SmoothTerrainConfiguration,
                         position: Vector3fc, xOffset: Float, zOffset: Float): Vector3fc {
    val p2 = vertexPosition(configuration, position.x(), position.z() + xOffset, Vector3.create())
    val p3 = vertexPosition(configuration, position.x() + zOffset, position.z(), Vector3.create())
    return Maths.calculateNormal(position, p2, p3)
}

private fun generateVertex(configuration: SmoothTerrainConfiguration, vertex: Vector2fc): SmoothVertex {
    val position: Vector3fc = vertexPosition(configuration, vertex.x(), vertex.y(), Vector3.create())
    val normal = vertexNormal(configuration, position, .1f, .1f)
    val colour = configuration.colourGenerator.generateColour(position, normal)
    val target = Vector3.of(0f, Math.random().toFloat() * 0.1f, 0f)

    return vertex(position, normal, colour, target)
}