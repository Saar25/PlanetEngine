package org.saar.minecraft.chunk

import org.saar.core.mesh.builder.ElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder

class ChunkMeshBuilder private constructor(
    private val builder: ElementsMeshBuilder<ChunkVertex>,
) : MeshBuilder {

    private val indices = intArrayOf(0, 1, 2, 0, 2, 3)

    private val vertexIds = arrayOf(
        intArrayOf(5, 7, 3, 1),
        intArrayOf(2, 6, 4, 0),
        intArrayOf(4, 6, 7, 5),
        intArrayOf(1, 3, 2, 0),
        intArrayOf(3, 7, 6, 2),
        intArrayOf(0, 4, 5, 1)
    )

    fun addBlock(x: Int, y: Int, z: Int, id: Int) = repeat(6) { addFace(x, y, z, id, it) }

    fun addFace(x: Int, y: Int, z: Int, id: Int, face: Int) {
        val faceIds = this.vertexIds[face]
        for (index in this.indices) {
            val vertex = Chunks.vertex(
                x, y, z, id, faceIds[index], face == 0)
            this.builder.addVertex(vertex)
        }
    }

    override fun load() = ChunkMesh(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: ChunkMeshPrototype = Chunks.meshPrototype()) =
            ChunkMeshBuilder(ArrayMeshBuilder.Dynamic(prototype))

        @JvmStatic
        @JvmOverloads
        fun fixed(vertices: Int, indices: Int, prototype: ChunkMeshPrototype = Chunks.meshPrototype()) =
            ChunkMeshBuilder(ArrayMeshBuilder.Fixed(vertices, indices, prototype))
    }
}