package org.saar.core.mesh

import org.saar.core.mesh.prototype.InstancedMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.ArraysDrawCall
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall
import org.saar.lwjgl.opengl.drawcall.InstancedElementsDrawCall
import org.saar.lwjgl.opengl.objects.vaos.IVao
import org.saar.lwjgl.opengl.objects.vaos.Vao

object Meshes {

    private fun toVao(prototype: MeshPrototype): IVao {
        val vao = Vao.create()
        with(MeshPrototypeHelper(prototype)) {
            store()
            loadToVao(vao)
        }
        return vao
    }

    @JvmStatic
    fun toArraysMesh(prototype: MeshPrototype, vertices: Int): ArraysMesh {
        return ArraysMesh(toVao(prototype), ArraysDrawCall(
            RenderMode.TRIANGLES, 0, vertices))
    }

    @JvmStatic
    fun toElementsMesh(prototype: MeshPrototype, indices: Int): ElementsMesh {
        return ElementsMesh(toVao(prototype), ElementsDrawCall(
            RenderMode.TRIANGLES, indices, DataType.U_INT))
    }

    @JvmStatic
    fun toInstancedArrayMesh(
        prototype: MeshPrototype, vertices: Int, instances: Int,
        renderMode: RenderMode = RenderMode.TRIANGLES,
    ): InstancedArraysMesh {
        return InstancedArraysMesh(toVao(prototype), InstancedArraysDrawCall(renderMode, vertices, instances))
    }

    @JvmStatic
    fun toInstancedElementsMesh(prototype: MeshPrototype, indices: Int, instances: Int): InstancedElementsMesh {
        return InstancedElementsMesh(toVao(prototype), InstancedElementsDrawCall(
            RenderMode.TRIANGLES, indices, DataType.U_INT, instances))
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
}