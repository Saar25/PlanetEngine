package org.saar.core.mesh.lod

import org.saar.core.mesh.Mesh

class LodMesh(private val meshes: List<Mesh>) : Mesh {

    val lod = ClampedInt(0, this.meshes.size)

    override fun draw() {
        val lod = this.lod.get()
        val mesh = this.meshes[lod]
        mesh.draw()
    }

    override fun delete() = this.meshes.forEach(Mesh::delete)
}
