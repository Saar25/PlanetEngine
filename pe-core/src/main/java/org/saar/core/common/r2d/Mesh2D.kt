package org.saar.core.common.r2d

import org.saar.core.mesh.Mesh

class Mesh2D internal constructor(private val mesh: Mesh) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()
}