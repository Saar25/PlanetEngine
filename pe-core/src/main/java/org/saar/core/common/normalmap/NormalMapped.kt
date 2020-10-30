package org.saar.core.common.normalmap

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.build.MeshBufferProperty
import org.saar.core.mesh.build.buffers.MeshIndexBuffer
import org.saar.core.mesh.build.buffers.MeshVertexBuffer
import org.saar.maths.utils.Vector3

object NormalMapped {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc, tangent: Vector3fc, biTangent: Vector3fc): NormalMappedVertex {
        return object : NormalMappedVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getUvCoord2f(): Vector2fc = uvCoord

            override fun getNormal3f(): Vector3fc = normal

            override fun getTangent3f(): Vector3fc = tangent

            override fun getBiTangent3f(): Vector3fc = biTangent
        }
    }

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc, tangent: Vector3fc): NormalMappedVertex {
        val biTangent: Vector3fc = Vector3.cross(normal, tangent)
        return vertex(position, uvCoord, normal, tangent, biTangent)
    }

    @JvmStatic
    fun mesh(vertex: MeshVertexBuffer, index: MeshIndexBuffer): NormalMappedMeshPrototype {
        return object : NormalMappedMeshPrototype {

            @MeshBufferProperty
            val meshVertexBuffer: MeshVertexBuffer = vertex

            @MeshBufferProperty
            val meshIndexBuffer: MeshIndexBuffer = index

            override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getUvCoordBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getNormalBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getTangentBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getBiTangentBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }

    @JvmStatic
    fun mesh(): NormalMappedMeshPrototype {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return mesh(vertex, index)
    }
}