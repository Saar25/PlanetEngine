package org.saar.core.mesh

interface UnloadedMesh {
    fun delete()

    fun load(): Mesh
}
