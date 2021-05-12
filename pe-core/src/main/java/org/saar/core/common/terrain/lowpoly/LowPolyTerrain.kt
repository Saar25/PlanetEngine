package org.saar.core.common.terrain.lowpoly

import org.joml.Vector2fc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.core.common.r3d.Mesh3D
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.terrain.Terrain
import org.saar.maths.utils.Maths
import org.saar.maths.utils.Vector3

private val v1 = Vector3.create()
private val v2 = Vector3.create()

class LowPolyTerrain(private val position: Vector2fc, private val configuration: LowPolyTerrainConfiguration) :
    Node3D(buildModel(position, configuration)), Terrain {

    override fun getHeight(x: Float, z: Float): Float {
        return this.configuration.heightGenerator.generateHeight(x, z)
    }
}


private fun vertexPosition(configuration: LowPolyTerrainConfiguration,
                           x: Float, z: Float, dest: Vector3f): Vector3fc {
    return dest.set(x, configuration.heightGenerator.generateHeight(x, z), z)
}

private fun vertexNormal(configuration: LowPolyTerrainConfiguration,
                         position: Vector3fc, xOffset: Float, zOffset: Float): Vector3fc {
    val p2 = vertexPosition(configuration, position.x(), position.z() + xOffset, v1)
    val p3 = vertexPosition(configuration, position.x() + zOffset, position.z(), v2)
    return Maths.calculateNormal(position, p2, p3)
}

private fun buildModel(position: Vector2fc, configuration: LowPolyTerrainConfiguration): Model3D {
    val indices = configuration.meshGenerator.generateIndices()
    val vertices = configuration.meshGenerator.generateVertices().map {
        val height = configuration.heightGenerator.generateHeight(
            it.x + position.x(), it.y + position.y())

        val tPosition = Vector3.of(it.x, height, it.y)
        val normal = vertexNormal(configuration, Vector3.of(tPosition).add(
            position.x(), 0f, position.y()), .01f, .01f)

        tPosition.mul(configuration.dimensions.x, configuration.amplitude, configuration.dimensions.y)

        val colour = configuration.colourGenerator.generateColour(tPosition, normal)

        R3D.vertex(tPosition, normal, colour)
    }

    val mesh = Mesh3D.load(vertices.toTypedArray(),
        indices.toIntArray(), arrayOf(R3D.instance()))

    return Model3D(mesh).also {
        it.transform.position.set(
            position.x() * configuration.dimensions.x, 0f,
            position.y() * configuration.dimensions.y)
    }
}