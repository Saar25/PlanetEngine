package org.saar.core.common.flatreflected

import org.saar.core.mesh.Mesh

class FlatReflectedMesh internal constructor(private val mesh: Mesh) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()
}