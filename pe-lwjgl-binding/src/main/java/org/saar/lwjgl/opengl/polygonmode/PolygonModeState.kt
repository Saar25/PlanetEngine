package org.saar.lwjgl.opengl.polygonmode

import org.saar.lwjgl.opengl.constants.Face

data class PolygonModeState(
    val face: Face,
    val mode: PolygonModeValue,
)