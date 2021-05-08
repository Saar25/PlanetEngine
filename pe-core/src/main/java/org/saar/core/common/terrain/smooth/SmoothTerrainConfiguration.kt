package org.saar.core.common.terrain.smooth

import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.height.HeightGenerator
import org.saar.core.common.terrain.mesh.MeshGenerator

data class SmoothTerrainConfiguration(
    val meshGenerator: MeshGenerator,
    val heightGenerator: HeightGenerator,
    val colourGenerator: ColourGenerator
)
