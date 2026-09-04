package org.saar.core.mesh.common

import org.saar.core.mesh.Mesh

object EmptyMesh : Mesh {

    override fun draw() = Unit

    override fun delete() = Unit
}