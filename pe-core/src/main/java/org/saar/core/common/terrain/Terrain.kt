package org.saar.core.common.terrain

import org.saar.core.node.Node

interface Terrain : Node {

    /**
     * Return the height of the terrain at world coordinates x and z
     *
     * @param x the x world coordinate
     * @param z the z world coordinate
     */
    fun getHeight(x: Float, z: Float): Float

    /**
     * Return whether the terrain contains the given coordinates
     *
     * @param x the x world coordinate
     * @param z the z world coordinate
     */
    fun contains(x: Float, z: Float): Boolean

}