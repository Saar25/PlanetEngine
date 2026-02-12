package org.saar.core.common.portal

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.buffer.DataMeshBufferBuilder
import org.saar.core.mesh.buffer.IndexMeshBufferBuilder
import org.saar.core.mesh.writer.writeIndices
import org.saar.core.mesh.writer.writeVertices
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder

// TODO: merge with FlatReflected pipeline and Texture3D
object Portal {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc) = object : PortalVertex {
        override val position3f = position
        override val uvCoord2f = uvCoord
    }

    @JvmStatic
    fun mesh(vertices: Array<PortalVertex>, indices: IntArray): Mesh {
        val vertexBufferBuilder = DataMeshBufferBuilder(
            FixedBufferBuilder(vertices.size * 5 * 4),
            VboUsage.STATIC_DRAW)

        val indexBufferBuilder = IndexMeshBufferBuilder(
            FixedBufferBuilder(indices.size * 4),
            VboUsage.STATIC_DRAW)

        val portalMeshBuilder = PortalMeshBuilder(indices.size,
            vertexBufferBuilder, vertexBufferBuilder, indexBufferBuilder)

        portalMeshBuilder.writer.writeVertices(vertices)
        portalMeshBuilder.writer.writeIndices(indices)

        return portalMeshBuilder.load()
    }
}