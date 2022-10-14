package org.saar.core.common.terrain

import org.joml.Vector2ic

interface TerrainFactory {

    fun create(position: Vector2ic): Terrain

}