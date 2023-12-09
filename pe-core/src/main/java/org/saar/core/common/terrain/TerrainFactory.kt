package org.saar.core.common.terrain

import org.joml.Vector3ic

interface TerrainFactory {

    fun create(position: Vector3ic): Terrain

}