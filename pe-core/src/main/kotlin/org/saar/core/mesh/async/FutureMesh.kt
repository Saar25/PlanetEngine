package org.saar.core.mesh.async

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.UnloadedMesh
import java.util.concurrent.CompletableFuture

class FutureMesh private constructor(private var helper: FutureMeshHelper) : Mesh {

    override fun draw() {
        this.helper = this.helper.next()
        this.helper.draw()
    }

    override fun delete() {
        this.helper = this.helper.next()
        this.helper.delete()
    }

    companion object {
        fun create(task: CompletableFuture<out Mesh>): FutureMesh {
            val helper = FutureMeshHelper.create(task)
            return FutureMesh(helper)
        }

        fun unloaded(task: CompletableFuture<out UnloadedMesh>): FutureMesh {
            val helper = FutureMeshHelper.unloaded(task)
            return FutureMesh(helper)
        }
    }
}
