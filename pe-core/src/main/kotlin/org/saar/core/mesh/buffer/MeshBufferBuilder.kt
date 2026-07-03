package org.saar.core.mesh.buffer

import org.saar.lwjgl.opengl.vbo.VboTarget
import org.saar.lwjgl.util.DataWriter

interface MeshBufferBuilder {

    val writer: DataWriter

    fun build(target: VboTarget): MeshBuffer

    fun delete()

}