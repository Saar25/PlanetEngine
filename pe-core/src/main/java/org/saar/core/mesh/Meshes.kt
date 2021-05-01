package org.saar.core.mesh

import org.saar.core.mesh.build.MeshPrototype
import org.saar.core.mesh.build.MeshPrototypeHelper
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
            loadToVao(vao)
            store()
        }
        return vao
    }

    @JvmStatic
    fun toArraysDrawCallMesh(prototype: MeshPrototype, vertices: Int): DrawCallMesh {
        return DrawCallMesh(toVao(prototype), ArraysDrawCall(
            RenderMode.TRIANGLES, 0, vertices))
    }

    @JvmStatic
    fun toElementsDrawCallMesh(prototype: MeshPrototype, indices: Int): DrawCallMesh {
        return DrawCallMesh(toVao(prototype), ElementsDrawCall(
            RenderMode.TRIANGLES, indices, DataType.U_INT))
    }

    @JvmStatic
    fun toInstancedDrawCallMesh(prototype: MeshPrototype, instances: Int): DrawCallMesh {
        return DrawCallMesh(toVao(prototype), InstancedArraysDrawCall(
            RenderMode.TRIANGLES, 0, instances))
    }

    @JvmStatic
    fun toInstancedElementsDrawCallMesh(prototype: MeshPrototype, indices: Int, instances: Int): DrawCallMesh {
        return DrawCallMesh(toVao(prototype), InstancedElementsDrawCall(
            RenderMode.TRIANGLES, indices, DataType.U_INT, instances))
    }

}