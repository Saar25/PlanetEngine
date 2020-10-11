package org.saar.core.common.r3d

import org.joml.Vector3fc
import org.saar.core.model.mesh.MeshBufferProperty
import org.saar.core.model.mesh.buffers.MeshIndexBuffer
import org.saar.core.model.mesh.buffers.MeshInstanceBuffer
import org.saar.core.model.mesh.buffers.MeshVertexBuffer
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

object R3D {

    @JvmStatic
    fun node(transform: Transform): Node3D {
        return Node3D { transform }
    }

    @JvmStatic
    fun node(): Node3D {
        return node(SimpleTransform())
    }

    @JvmStatic
    fun vertex(position: Vector3fc, normal: Vector3fc, colour: Vector3fc): Vertex3D {
        return object : Vertex3D {
            override fun getPosition3f(): Vector3fc = position

            override fun getNormal3f(): Vector3fc = normal

            override fun getColour3f(): Vector3fc = colour
        }
    }

    @JvmStatic
    fun mesh(meshVertexBuffer: MeshVertexBuffer,
             meshInstanceBuffer: MeshInstanceBuffer,
             meshIndexBuffer: MeshIndexBuffer): Mesh3DPrototype {
        return object : Mesh3DPrototype {

            @MeshBufferProperty
            val meshVertexBuffer: MeshVertexBuffer = meshVertexBuffer

            @MeshBufferProperty
            val meshInstanceBuffer: MeshInstanceBuffer = meshInstanceBuffer

            @MeshBufferProperty
            val meshIndexBuffer: MeshIndexBuffer = meshIndexBuffer

            override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getNormalBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getColourBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getTransformBuffer(): MeshInstanceBuffer = this.meshInstanceBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }

    @JvmStatic
    fun mesh(): Mesh3DPrototype {
        val instance = MeshInstanceBuffer.createStatic()
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return mesh(vertex, instance, index)
    }

}