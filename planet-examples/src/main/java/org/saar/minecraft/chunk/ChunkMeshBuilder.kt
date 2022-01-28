package org.saar.minecraft.chunk

import org.saar.core.mesh.builder.ArraysMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder

class ChunkMeshBuilder private constructor(
    private val builder: ArraysMeshBuilder<ChunkVertex>,
) : MeshBuilder {

    fun addBlock(x: Int, y: Int, z: Int, id: Int, shadow: Int) = repeat(6) { addFace(x, y, z, id, it, shadow) }

    fun addFace(x: Int, y: Int, z: Int, id: Int, face: Int, shadow: Int) {
        repeat(6) {
            val vertex = Chunks.vertex(
                x, y, z, id, face, shadow)
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