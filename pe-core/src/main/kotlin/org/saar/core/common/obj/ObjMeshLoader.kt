package org.saar.core.common.obj

import org.saar.lwjgl.assimp.AssimpUtil
import org.saar.lwjgl.assimp.component.AssimpIndexComponent
import org.saar.lwjgl.assimp.component.AssimpNormalComponent
import org.saar.lwjgl.assimp.component.AssimpPositionComponent
import org.saar.lwjgl.assimp.component.AssimpTexCoordComponent

class ObjMeshLoader(private val objFile: String) : AutoCloseable {

    private val assimpMesh = AssimpUtil.load(this@ObjMeshLoader.objFile)

    fun loadVertices(): Array<ObjVertex> = loadVerticesSequence().toList().toTypedArray()

    fun loadVerticesSequence(): Sequence<ObjVertex> = sequence {
        val assimpPosition = AssimpPositionComponent.of(
            this@ObjMeshLoader.assimpMesh.aiMesh)
        val assimpTexCoord = AssimpTexCoordComponent.of(
            this@ObjMeshLoader.assimpMesh.aiMesh)
        val assimpNormal = AssimpNormalComponent.of(
            this@ObjMeshLoader.assimpMesh.aiMesh)

        for (i in 0 until assimpMesh.vertexCount()) {
            yield(Obj.vertex(
                assimpPosition.next(),
                assimpTexCoord.next(),
                assimpNormal.next()
            ))
        }
    }

    fun loadIndices(): IntArray = loadIndicesSequence().toList().toIntArray()

    fun loadIndicesSequence(): Sequence<Int> = sequence {
        val assimpIndex = AssimpIndexComponent.of(
            this@ObjMeshLoader.assimpMesh.aiMesh)

        for (i in 0 until assimpMesh.indexCount()) {
            yield(assimpIndex.next())
        }
    }

    fun indexCount() = this.assimpMesh.indexCount()

    fun vertexCount() = this.assimpMesh.vertexCount()

    override fun close() = this.assimpMesh.close()
}