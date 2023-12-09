package org.saar.core.common.terrain.height

interface HeightGenerator {

    fun generate(x: Float, y: Float, z: Float): Float

}