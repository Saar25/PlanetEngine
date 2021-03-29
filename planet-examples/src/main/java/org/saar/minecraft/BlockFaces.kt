package org.saar.minecraft

class BlockFaces(xPosId: Int, xNegId: Int, yPosId: Int, yNegId: Int, zPosId: Int, zNegId: Int) {

    private val faces: Array<Int> = arrayOf(xPosId, xNegId, yPosId, yNegId, zPosId, zNegId)

    constructor(top: Int, sides: Int, bottom: Int) : this(sides, sides, top, bottom, sides, sides)

    constructor(topBottom: Int, sides: Int) : this(sides, sides, topBottom, topBottom, sides, sides)

    constructor(all: Int) : this(all, all, all, all, all, all)

    fun faceId(id: Int): Int = this.faces[id]
}