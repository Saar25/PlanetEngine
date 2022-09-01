package org.saar.core.mesh.buffer

import org.saar.lwjgl.opengl.vao.WritableVao
import org.saar.lwjgl.util.DataReader
import org.saar.lwjgl.util.DataWriter

interface MeshBuffer {

    val writer: DataWriter

    val reader: DataReader

    fun store(offset: Long)

    fun loadInVao(vao: WritableVao)

    fun delete()
}