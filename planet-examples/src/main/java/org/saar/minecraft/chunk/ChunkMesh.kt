package org.saar.minecraft.chunk

import org.saar.core.mesh.Mesh

class ChunkMesh(private val mesh: Mesh) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()
}