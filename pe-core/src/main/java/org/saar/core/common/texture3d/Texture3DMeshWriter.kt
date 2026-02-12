package org.saar.core.common.texture3d

import org.saar.core.mesh.writer.IndexedMeshWriter
import org.saar.core.mesh.writer.VertexMeshWriter
import org.saar.lwjgl.util.DataWriter

class Texture3DMeshWriter(
    private val positionWriter: DataWriter,
    private val uvCoordWriter: DataWriter,
    private val indexWriter: DataWriter,
) : VertexMeshWriter<Texture3DVertex>, IndexedMeshWriter {

    override fun writeVertex(vertex: Texture3DVertex) {
        this.positionWriter.write3f(vertex.position3f)
        this.uvCoordWriter.write2f(vertex.uvCoord2f)
    }

    override fun writeIndex(index: Int) = this.indexWriter.writeInt(index)
}