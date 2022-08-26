package org.saar.core.common.normalmap

import org.saar.core.mesh.prototype.IndexedMeshReader
import org.saar.core.mesh.prototype.VertexMeshReader
import org.saar.lwjgl.util.DataReader

class NormalMappedMeshReader(
    private val positionReader: DataReader,
    private val uvCoordReader: DataReader,
    private val normalReader: DataReader,
    private val tangentReader: DataReader,
    private val biTangentReader: DataReader,
    private val indexReader: DataReader,
) : VertexMeshReader<NormalMappedVertex>, IndexedMeshReader {

    override fun readVertex() = NormalMapped.vertex(
        this.positionReader.read3f(),
        this.uvCoordReader.read2f(),
        this.normalReader.read3f(),
        this.tangentReader.read3f(),
        this.biTangentReader.read3f(),
    )

    override fun readIndex() = this.indexReader.readInt()
}