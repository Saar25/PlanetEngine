package org.saar.minecraft

interface Block {
    val id: Int
    val name: String
    val isSolid: Boolean
    val isTransparent: Boolean
    val isCollideable: Boolean
    val faces: BlockFaces
}