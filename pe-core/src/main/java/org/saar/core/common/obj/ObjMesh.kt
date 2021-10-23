package org.saar.core.common.obj

import org.saar.core.mesh.Mesh

class ObjMesh internal constructor(private val mesh: Mesh) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()
}