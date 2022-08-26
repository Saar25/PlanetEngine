package org.saar.core.common.r2d

import org.saar.core.mesh.builder.IndexedMeshWriter
import org.saar.core.mesh.builder.VertexMeshWriter
import org.saar.lwjgl.util.DataWriter

class MeshWriter2D(
    private val positionWriter: DataWriter,
    private val colourWriter: DataWriter,
    private val indexWriter: DataWriter,
) : VertexMeshWriter<Vertex2D>, IndexedMeshWriter {

    override fun writeVertex(vertex: Vertex2D) {
        this.positionWriter.write2f(vertex.position2f)
        this.colourWriter.write3f(vertex.colour3f)
    }

    override fun writeIndex(index: Int) = this.indexWriter.writeInt(index)
}