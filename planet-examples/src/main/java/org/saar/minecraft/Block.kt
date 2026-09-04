package org.saar.minecraft

interface Block {
    val id: Int
    val name: String
    val isSolid: Boolean
    val isTransparent: Boolean
    val isCollideable: Boolean
    val lightPropagation: LightPropagation get() = LightPropagation.NONE
    val faces: BlockFaces
}