package org.saar.core.common.terrain.lowpoly

import org.joml.Vector2fc
import org.joml.Vector2ic
import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.terrain.TerrainFactory
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.height.HeightGenerator
import org.saar.core.common.terrain.mesh.MeshGenerator
import org.saar.maths.utils.Maths
import org.saar.maths.utils.Vector3

class LowPolyTerrainFactory(
    private val meshGenerator: MeshGenerator,
    private val heightGenerator: HeightGenerator,
    private val colourGenerator: ColourGenerator,
    private val dimensions: Vector2fc,
) : TerrainFactory {

    private val v1 = Vector3.create()
    private val v2 = Vector3.create()
    private val offset: Float = 0.01f

    override fun create(position: Vector2ic): LowPolyTerrain {
        val model = buildModel(position)

        return LowPolyTerrain(position, this.dimensions, this.heightGenerator, model)
    }

    private fun vertexPosition(x: Float, z: Float, dest: Vector3f): Vector3fc {
        return dest.set(x, this.heightGenerator.generateHeight(x, z), z)
    }

    private fun vertexNormal(position: Vector3fc): Vector3fc {
        val p2 = vertexPosition(position.x(), position.z() + this.offset, this.v1)
        val p3 = vertexPosition(position.x() + this.offset, position.z(), this.v2)
        return Maths.calculateNormal(position, p2, p3)
    }

    private fun buildModel(position: Vector2ic): Model3D {
        val indices = this.meshGenerator.generateIndices()
        val vertices = this.meshGenerator.generateVertices().map {
            val lx = it.x * this.dimensions.x()
            val lz = it.y * this.dimensions.y()

            val wx = lx + position.x() * this.dimensions.x()
            val wz = lz + position.y() * this.dimensions.y()

            val height = this.heightGenerator.generateHeight(wx, wz)

            val lPosition = Vector3.of(lx, height, lz)
            val wPosition = Vector3.of(wx, height, wz)

            val normal = vertexNormal(wPosition)

            val colour = this.colourGenerator.generateColour(wPosition, normal)

            R3D.vertex(lPosition, normal, colour)
        }

        val mesh = R3D.mesh(arrayOf(R3D.instance()),
            vertices.toTypedArray(), indices.toIntArray())

        return Model3D(mesh).also {
            it.specular = 0f
            it.transform.position.set(
                position.x() * this.dimensions.x(), 0f,
                position.y() * this.dimensions.y())
        }
    }
}