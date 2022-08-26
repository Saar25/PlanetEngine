package org.saar.core.common.obj

import org.saar.core.mesh.builder.IndexedMeshReader
import org.saar.core.mesh.builder.VertexMeshReader
import org.saar.lwjgl.util.DataReader

class ObjMeshReader(
    private val positionReader: DataReader,
    private val uvCoordReader: DataReader,
    private val normalReader: DataReader,
    private val indexReader: DataReader,
) : VertexMeshReader<ObjVertex>, IndexedMeshReader {

    override fun readVertex() = Obj.vertex(
        this.positionReader.read3f(),
        this.uvCoordReader.read2f(),
        this.normalReader.read3f()
    )

    override fun readIndex() = this.indexReader.readInt()
}