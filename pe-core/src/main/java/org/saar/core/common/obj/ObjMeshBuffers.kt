package org.saar.core.common.obj

import org.saar.lwjgl.util.buffer.LwjglBuffer

class ObjMeshBuffers(
    val positionBuffer: LwjglBuffer,
    val uvCoordBuffer: LwjglBuffer,
    val normalBuffer: LwjglBuffer,
    val indexBuffer: LwjglBuffer,
) {

    val writer = ObjMeshWriter(
        this.positionBuffer.writer,
        this.uvCoordBuffer.writer,
        this.normalBuffer.writer,
        this.indexBuffer.writer,
    )

    val reader = ObjMeshReader(
        this.positionBuffer.reader,
        this.uvCoordBuffer.reader,
        this.normalBuffer.reader,
        this.indexBuffer.reader,
    )

    val vertexBuffers = listOf(this.positionBuffer, this.uvCoordBuffer, this.normalBuffer).distinct()

    val indexBuffers = listOf(this.indexBuffer)

}