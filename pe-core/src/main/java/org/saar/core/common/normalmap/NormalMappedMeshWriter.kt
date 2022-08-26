package org.saar.core.common.normalmap

import org.saar.core.mesh.builder.IndexedMeshWriter
import org.saar.core.mesh.builder.VertexMeshWriter
import org.saar.lwjgl.util.DataWriter

class NormalMappedMeshWriter(
    private val positionWriter: DataWriter,
    private val uvCoordWriter: DataWriter,
    private val normalWriter: DataWriter,
    private val tangentWriter: DataWriter,
    private val biTangentWriter: DataWriter,
    private val indexWriter: DataWriter,
) : VertexMeshWriter<NormalMappedVertex>, IndexedMeshWriter {

    override fun writeVertex(vertex: NormalMappedVertex) {
        this.positionWriter.write3f(vertex.position3f)
        this.uvCoordWriter.write2f(vertex.uvCoord2f)
        this.normalWriter.write3f(vertex.normal3f)
        this.tangentWriter.write3f(vertex.tangent3f)
        this.biTangentWriter.write3f(vertex.biTangent3f)
    }

    override fun writeIndex(index: Int) = this.indexWriter.writeInt(index)
}