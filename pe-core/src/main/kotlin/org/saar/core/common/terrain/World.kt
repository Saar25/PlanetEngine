package org.saar.core.common.terrain

import org.saar.core.node.ParentNode

interface World : ParentNode {

    override val children: List<Terrain>

    fun getHeight(x: Float, y: Float, z: Float): Float

}