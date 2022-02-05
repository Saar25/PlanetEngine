package org.saar.minecraft.chunk

import org.saar.core.mesh.builder.ArraysMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder

class ChunkMeshBuilder private constructor(
    private val builder: ArraysMeshBuilder<ChunkVertex>,
) : MeshBuilder {

    private val indexMap = arrayOf(0, 1, 2, 0, 2, 3)

    fun addBlock(x: Int, y: Int, z: Int, id: Int, shadow: Int) =
        repeat(6) { addFace(x, y, z, id, it, shadow, BooleanArray(4)) }

    fun addFace(x: Int, y: Int, z: Int, id: Int, face: Int, light: Int, ao: BooleanArray) {
        repeat(6) {
            val vertex = Chunks.vertex(
                x, y, z, id, face, light, ao[indexMap[it]])
            this.builder.addVertex(vertex)
        }
    }

    override fun load() = ChunkMesh(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: ChunkMeshPrototype = Chunks.meshPrototype()) =
            ChunkMeshBuilder(ArraysMeshBuilder.Dynamic(prototype))

        @JvmStatic
        @JvmOverloads
        fun fixed(vertices: Int, prototype: ChunkMeshPrototype = Chunks.meshPrototype()) =
            ChunkMeshBuilder(ArraysMeshBuilder.Fixed(vertices, prototype))
    }
}