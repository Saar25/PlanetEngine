package org.saar.core.common.flatreflected

import org.saar.core.mesh.writer.IndexedMeshWriter
import org.saar.core.mesh.writer.VertexMeshWriter
import org.saar.lwjgl.util.DataWriter

class FlatReflectedMeshWriter(
    private val positionWriter: DataWriter,
    private val indexWriter: DataWriter,
) : VertexMeshWriter<FlatReflectedVertex>, IndexedMeshWriter {

    override fun writeVertex(vertex: FlatReflectedVertex) {
        this.positionWriter.write3f(vertex.position3f)
    }

    override fun writeIndex(index: Int) = this.indexWriter.writeInt(index)
}