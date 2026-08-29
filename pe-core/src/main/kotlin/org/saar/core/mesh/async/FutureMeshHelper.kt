package org.saar.core.mesh.async

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.UnloadedMesh
import java.util.concurrent.CompletableFuture

internal interface FutureMeshHelper : Mesh {

    fun next(): FutureMeshHelper

    override fun draw()

    override fun delete()

    private class Running(private val task: CompletableFuture<out Mesh>) : FutureMeshHelper {
        override fun next(): FutureMeshHelper {
            if (this.task.isDone) {
                val mesh = this.task.getNow(null)
                return Finished(mesh)
            }
            return this
        }

        override fun draw() = Unit

        override fun delete() {
            this.task.cancel(true)
        }
    }

    private class Unloaded(private val task: CompletableFuture<out UnloadedMesh>) : FutureMeshHelper {
        override fun next(): FutureMeshHelper {
            if (this.task.isDone) {
                val unloaded = this.task.getNow(null)
                val mesh = unloaded.load()
                return Finished(mesh)
            }
            return this
        }

        override fun draw() = Unit

        override fun delete() {
            this.task.cancel(true)
        }
    }

    private class Finished(private val mesh: Mesh) : FutureMeshHelper {
        override fun next() = this

        override fun draw() = this.mesh.draw()

        override fun delete() = this.mesh.delete()
    }


    companion object {
        fun create(task: CompletableFuture<out Mesh>): FutureMeshHelper = Running(task)

        fun unloaded(task: CompletableFuture<out UnloadedMesh>): FutureMeshHelper = Unloaded(task)
    }
}
