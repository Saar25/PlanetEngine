package org.saar.core.mesh

import org.saar.core.mesh.prototype.IndexedVertexMeshPrototype
import org.saar.core.mesh.prototype.InstancedIndexedVertexMeshPrototype
import org.saar.core.mesh.prototype.InstancedMeshPrototype
import org.saar.core.mesh.prototype.VertexMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.ArraysDrawCall
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall
import org.saar.lwjgl.opengl.drawcall.InstancedElementsDrawCall
import org.saar.lwjgl.opengl.vaos.Vao

object Meshes {

    @JvmStatic
    fun toArraysMesh(prototype: VertexMeshPrototype<*>, vertices: Int): ArraysMesh {
        val vao = Vao.create()

        prototype.vertexBuffers.forEach { it.store(0) }
        prototype.vertexBuffers.forEach { it.loadInVao(vao) }

        return ArraysMesh(vao, ArraysDrawCall(
            RenderMode.TRIANGLES, 0, vertices))
    }

    @JvmStatic
    fun toElementsMesh(prototype: IndexedVertexMeshPrototype<*>, indices: Int): ElementsMesh {
        val vao = Vao.create()

        prototype.vertexBuffers.forEach { it.store(0) }
        prototype.vertexBuffers.forEach { it.loadInVao(vao) }

        prototype.indexBuffers.forEach { it.store(0) }
        prototype.indexBuffers.forEach { it.loadInVao(vao) }

        return ElementsMesh(vao, ElementsDrawCall(
            RenderMode.TRIANGLES, indices, DataType.U_INT))
    }

    @JvmStatic
    fun toInstancedArrayMesh(
        prototype: InstancedMeshPrototype<*>, vertices: Int, instances: Int,
        renderMode: RenderMode = RenderMode.TRIANGLES,
    ): InstancedArraysMesh {
        val vao = Vao.create()

        prototype.instanceBuffers.forEach { it.store(0) }
        prototype.instanceBuffers.forEach { it.loadInVao(vao) }

        return InstancedArraysMesh(vao, InstancedArraysDrawCall(renderMode, vertices, instances))
    }

    @JvmStatic
    fun toInstancedElementsMesh(
        prototype: InstancedIndexedVertexMeshPrototype<*, *>,
        indices: Int,
        instances: Int,
    ): InstancedElementsMesh {
        val vao = Vao.create()

        prototype.vertexBuffers.forEach { it.store(0) }
        prototype.vertexBuffers.forEach { it.loadInVao(vao) }

        prototype.instanceBuffers.forEach { it.store(0) }
        prototype.instanceBuffers.forEach { it.loadInVao(vao) }

        prototype.indexBuffers.forEach { it.store(0) }
        prototype.indexBuffers.forEach { it.loadInVao(vao) }

        return InstancedElementsMesh(vao, InstancedElementsDrawCall(
            RenderMode.TRIANGLES, indices, DataType.U_INT, instances))
    }
}