package org.saar.core.common.portal

import org.saar.core.mesh.writer.IndexedMeshWriter
import org.saar.core.mesh.writer.VertexMeshWriter
import org.saar.lwjgl.util.DataWriter

class PortalMeshWriter(
    private val positionWriter: DataWriter,
    private val indexWriter: DataWriter,
) : VertexMeshWriter<PortalVertex>, IndexedMeshWriter {

    override fun writeVertex(vertex: PortalVertex) {
        this.positionWriter.write3f(vertex.position3f)
    }

    override fun writeIndex(index: Int) = this.indexWriter.writeInt(index)
}