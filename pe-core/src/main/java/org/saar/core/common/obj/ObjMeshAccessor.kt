package org.saar.core.common.obj

import org.saar.core.mesh.prototype.IndexedMeshReader
import org.saar.core.mesh.prototype.IndexedMeshWriter
import org.saar.core.mesh.prototype.VertexMeshReader
import org.saar.core.mesh.prototype.VertexMeshWriter

class ObjMeshAccessor(private val prototype: ObjMeshBuffers) :
    VertexMeshReader<ObjVertex>, VertexMeshWriter<ObjVertex>, IndexedMeshReader, IndexedMeshWriter {

    override fun writeVertex(vertex: ObjVertex) {
        this.prototype.positionBuffer.writer.write3f(vertex.position3f)
        this.prototype.uvCoordBuffer.writer.write2f(vertex.uvCoord2f)
        this.prototype.normalBuffer.writer.write3f(vertex.normal3f)
    }

    override fun readVertex(): ObjVertex {
        val position = this.prototype.positionBuffer.reader.read3f()
        val uvCoord = this.prototype.uvCoordBuffer.reader.read2f()
        val normal = this.prototype.normalBuffer.reader.read3f()
        return Obj.vertex(position, uvCoord, normal)
    }

    override fun writeIndex(index: Int) {
        this.prototype.indexBuffer.writer.writeInt(index)
    }

    override fun readIndex(): Int {
        return this.prototype.indexBuffer.reader.readInt()
    }
}