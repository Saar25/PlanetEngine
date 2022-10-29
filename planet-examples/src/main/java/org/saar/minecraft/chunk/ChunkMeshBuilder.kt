package org.saar.minecraft.chunk

import org.saar.core.mesh.DrawCallMesh
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.MeshBuilder
import org.saar.core.mesh.buffer.DataMeshBufferBuilder
import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.ArraysDrawCall
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.lwjgl.opengl.vbo.VboTarget


class ChunkMeshBuilder(
    private val vertices: Int,
    private val dataBufferBuilder: DataMeshBufferBuilder,
) : MeshBuilder {

    private val indexMap = arrayOf(0, 1, 2, 0, 2, 3)

    fun addBlock(x: Int, y: Int, z: Int, id: Int, shadow: Int) =
        repeat(6) { addFace(x, y, z, id, it, shadow, BooleanArray(4)) }

    fun addFace(x: Int, y: Int, z: Int, id: Int, face: Int, light: Int, ao: BooleanArray) {
        repeat(6) {
            val vertex = Chunks.vertex(
                x, y, z, id, face, light, ao[indexMap[it]])
            this.writer.writeVertex(vertex)
        }
    }

    val writer = ChunkMeshWriter(this.dataBufferBuilder.writer)

    init {
        this.dataBufferBuilder.addAttribute(
            Attributes.ofInteger(0, 1, DataType.U_INT))
    }

    override fun delete() = this.dataBufferBuilder.delete()

    override fun load(): Mesh {
        val vao = Vao.create()

        this.dataBufferBuilder.build(VboTarget.ARRAY_BUFFER).apply {
            store(0)
            loadInVao(vao)
        }

        val drawCall = ArraysDrawCall(RenderMode.TRIANGLES, 0, this.vertices)
        return DrawCallMesh(vao, drawCall)
    }
}