package org.saar.minecraft.entity

// https://technical-minecraft.fandom.com/wiki/Entity_Hitbox_Sizes
object HitBoxes {

    @JvmStatic
    val player: HitBox = HitBox.build(0.3f, 1.8f)

}