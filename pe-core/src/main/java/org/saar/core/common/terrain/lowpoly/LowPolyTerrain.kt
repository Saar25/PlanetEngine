package org.saar.core.common.terrain.lowpoly

import org.joml.Vector2fc
import org.joml.Vector2ic
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.terrain.Terrain
import org.saar.core.common.terrain.height.HeightGenerator
import org.saar.maths.utils.Maths

class LowPolyTerrain(private val position: Vector2ic,
                     private val dimensions: Vector2fc,
                     private val heightGenerator: HeightGenerator,
                     model: Model3D) :
    Node3D(model), Terrain {

    override fun getHeight(x: Float, y: Float, z: Float): Float {
        return this.heightGenerator.generate(x, y, z)
    }

    override fun contains(x: Float, y: Float, z: Float): Boolean {
        val wxs = this.position.x() * this.dimensions.x()
        val wzs = this.position.y() * this.dimensions.y()
        val wxe = wxs + this.dimensions.x()
        val wze = wzs + this.dimensions.y()

        return Maths.isInside(x, wxs, wxe) && Maths.isInside(z, wzs, wze)
    }
}