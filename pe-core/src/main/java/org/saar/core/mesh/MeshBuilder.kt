package org.saar.core.mesh

import org.saar.lwjgl.opengl.vao.Vao

interface MeshBuilder {
    fun load(): Mesh

    fun loadVao(): Vao

    fun delete()
}