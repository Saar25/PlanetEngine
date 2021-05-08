package org.saar.core.common.terrain.lowpoly

import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.core.common.r3d.Mesh3D
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.R3D
import org.saar.core.node.Node
import org.saar.maths.utils.Maths
import org.saar.maths.utils.Vector3

private val v1 = Vector3.create()
private val v2 = Vector3.create()

class LowPolyTerrain(private val configuration: LowPolyTerrainConfiguration) : Node {

    val model: Model3D = buildModel(configuration)

    private fun vertexPosition(x: Float, z: Float, dest: Vector3f): Vector3fc {
        return dest.set(x, this.configuration.heightGenerator.generateHeight(x, z), z)
    }

    private fun vertexNormal(position: Vector3fc, xOffset: Float, zOffset: Float): Vector3fc {
        val p2 = vertexPosition(position.x(), position.z() + xOffset, v1)
        val p3 = vertexPosition(position.x() + zOffset, position.z(), v2)
        return Maths.calculateNormal(position, p2, p3)
    }

    private fun buildModel(configuration: LowPolyTerrainConfiguration): Model3D {
        val indices = configuration.meshGenerator.generateIndices()
        val vertices = configuration.meshGenerator.generateVertices().map {
            val height = configuration.heightGenerator.generateHeight(
                it.x + configuration.position.x, it.y + configuration.position.y)

            val position = Vector3.of(it.x, height, it.y)
            val normal = vertexNormal(Vector3.of(position).add(
                configuration.position.x, 0f, configuration.position.y), .01f, .01f)

            position.mul(configuration.dimensions.x, configuration.amplitude, configuration.dimensions.y)

            val colour = configuration.colourGenerator.generateColour(position, normal)

            R3D.vertex(position, normal, colour)
        }

        val mesh = Mesh3D.load(vertices.toTypedArray(),
            indices.toIntArray(), arrayOf(R3D.instance()))

        return Model3D(mesh).also {
            it.transform.position.set(
                configuration.position.x * configuration.dimensions.x, 0f,
                configuration.position.y * configuration.dimensions.y)
        }
    }

    override fun delete() {
        this.model.delete()
    }
}
