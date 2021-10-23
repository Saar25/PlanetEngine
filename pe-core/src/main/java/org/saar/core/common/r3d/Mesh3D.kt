package org.saar.core.common.r3d

import org.saar.core.mesh.Mesh

class Mesh3D internal constructor(private val mesh: Mesh) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()
}