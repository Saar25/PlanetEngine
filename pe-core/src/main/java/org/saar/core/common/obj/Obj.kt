package org.saar.core.common.obj

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.build.MeshBufferProperty
import org.saar.core.mesh.build.buffers.MeshIndexBuffer
import org.saar.core.mesh.build.buffers.MeshVertexBuffer

object Obj {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc): ObjVertex {
        return object : ObjVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getUvCoord2f(): Vector2fc = uvCoord

            override fun getNormal3f(): Vector3fc = normal
        }
    }

    @JvmStatic
    fun mesh(vertex: MeshVertexBuffer, index: MeshIndexBuffer): ObjMeshPrototype {
        return object : ObjMeshPrototype {

            @MeshBufferProperty
            val meshVertexBuffer: MeshVertexBuffer = vertex

            @MeshBufferProperty
            val meshIndexBuffer: MeshIndexBuffer = index

            override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getUvCoordBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getNormalBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }

    @JvmStatic
    fun mesh(): ObjMeshPrototype {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return mesh(vertex, index)
    }

}