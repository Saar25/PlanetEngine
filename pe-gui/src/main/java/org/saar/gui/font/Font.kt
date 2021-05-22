package org.saar.gui.font

import org.saar.lwjgl.opengl.textures.Texture2D

interface Font {
    val size: Float
    val bitmap: Texture2D
    val characters: List<FontCharacter>
    val lineGap: Float

    val lineHeight: Float get() = this.size + this.lineGap

    fun delete() = this.bitmap.delete()

    val defaultCharacter get() = this.characters[0]

    fun getCharacter(char: Char) = this.characters.find { it.char == char }

    fun getCharacterOrDefault(char: Char) = getCharacter(char) ?: this.defaultCharacter
}
