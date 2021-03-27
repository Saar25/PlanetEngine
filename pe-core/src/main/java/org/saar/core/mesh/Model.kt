package org.saar.core.mesh

interface Model {
    val mesh: Mesh

    fun draw() = mesh.draw()

    fun delete() = mesh.delete()
}