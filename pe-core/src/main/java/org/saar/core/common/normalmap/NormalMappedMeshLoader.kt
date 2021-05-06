package org.saar.core.common.normalmap

import org.saar.lwjgl.assimp.AssimpUtil
import org.saar.lwjgl.assimp.component.*

class NormalMappedMeshLoader(private val objFile: String) : AutoCloseable {

    private val assimpMesh = AssimpUtil.load(this@NormalMappedMeshLoader.objFile)

    fun loadVertices(): List<NormalMappedVertex> = loadVerticesSequence().toList()

    fun loadVerticesSequence(): Sequence<NormalMappedVertex> = sequence {
        val assimpPosition = AssimpPositionComponent.of(
            this@NormalMappedMeshLoader.assimpMesh.aiMesh)
        val assimpTexCoord = AssimpTexCoordComponent.of(
            this@NormalMappedMeshLoader.assimpMesh.aiMesh)
        val assimpNormal = AssimpNormalComponent.of(
            this@NormalMappedMeshLoader.assimpMesh.aiMesh)
        val assimpTangent = AssimpTangentComponent.of(
            this@NormalMappedMeshLoader.assimpMesh.aiMesh)
        val assimpBiTangent = AssimpBiTangentComponent.of(
            this@NormalMappedMeshLoader.assimpMesh.aiMesh)

        for (i in 0 until assimpMesh.vertexCount()) {
            yield(NormalMapped.vertex(
                assimpPosition.next(),
                assimpTexCoord.next(),
                assimpNormal.next(),
                assimpTangent.next(),
                assimpBiTangent.next()
            ))
        }
    }

    fun loadIndices(): List<Int> = loadIndicesSequence().toList()

    fun loadIndicesSequence(): Sequence<Int> = sequence {
        val assimpIndex = AssimpIndexComponent.of(
            this@NormalMappedMeshLoader.assimpMesh.aiMesh)

        for (i in 0 until assimpMesh.indexCount()) {
            yield(assimpIndex.next())
        }
    }

    fun indexCount() = this.assimpMesh.indexCount()

    fun vertexCount() = this.assimpMesh.vertexCount()

    override fun close() = this.assimpMesh.close()
}