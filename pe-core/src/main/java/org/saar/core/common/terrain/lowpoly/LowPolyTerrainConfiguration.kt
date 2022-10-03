package org.saar.core.common.terrain.lowpoly

import org.joml.Vector2f
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.height.HeightGenerator
import org.saar.core.common.terrain.mesh.MeshGenerator

data class LowPolyTerrainConfiguration(
    val meshGenerator: MeshGenerator,
    val heightGenerator: HeightGenerator,
    val colourGenerator: ColourGenerator,
    val dimensions: Vector2f,
)
