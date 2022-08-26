package org.saar.core.common.r3d

import org.saar.core.mesh.builder.IndexedMeshWriter
import org.saar.core.mesh.builder.InstancedMeshWriter
import org.saar.core.mesh.builder.VertexMeshWriter
import org.saar.lwjgl.util.DataWriter

class MeshWriter3D(
    private val positionWriter: DataWriter,
    private val normalWriter: DataWriter,
    private val colourWriter: DataWriter,
    private val transformWriter: DataWriter,
    private val indexWriter: DataWriter,
) : VertexMeshWriter<Vertex3D>, InstancedMeshWriter<Instance3D>, IndexedMeshWriter {

    override fun writeVertex(vertex: Vertex3D) {
        this.positionWriter.write3f(vertex.position3f)
        this.normalWriter.write3f(vertex.normal3f)
        this.colourWriter.write3f(vertex.colour3f)
    }

    override fun writeInstance(instance: Instance3D) {
        this.transformWriter.write4x4f(instance.transform.transformationMatrix)
    }

    override fun writeIndex(index: Int) = this.indexWriter.writeInt(index)
}