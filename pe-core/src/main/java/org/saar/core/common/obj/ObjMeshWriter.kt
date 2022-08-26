package org.saar.core.common.obj

import org.saar.core.mesh.prototype.IndexedMeshWriter
import org.saar.core.mesh.prototype.VertexMeshWriter
import org.saar.lwjgl.util.DataWriter

class ObjMeshWriter(
    private val positionWriter: DataWriter,
    private val uvCoordWriter: DataWriter,
    private val normalWriter: DataWriter,
    private val indexWriter: DataWriter,
) : VertexMeshWriter<ObjVertex>, IndexedMeshWriter {

    override fun writeVertex(vertex: ObjVertex) {
        this.positionWriter.write3f(vertex.position3f)
        this.uvCoordWriter.write2f(vertex.uvCoord2f)
        this.normalWriter.write3f(vertex.normal3f)
    }

    override fun writeIndex(index: Int) = this.indexWriter.writeInt(index)
}