package org.saar.core.mesh

interface Model {
    val mesh: Mesh

    fun draw() = this.mesh.draw()

    fun delete() = this.mesh.delete()
}