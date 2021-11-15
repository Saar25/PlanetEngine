package org.saar.core.common.normalmap

import org.saar.core.mesh.Mesh

class NormalMappedMesh internal constructor(private val mesh: Mesh) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()
}