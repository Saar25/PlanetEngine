package org.saar.gui.font

import org.saar.lwjgl.opengl.textures.Texture2D

interface Font {
    val bitmap: Texture2D
    val characters: List<FontCharacter>
}

fun Font.delete() = this.bitmap.delete()

val Font.defaultCharacter get() = this.characters[0]

fun Font.getCharacter(char: Char) = this.characters.find { it.char == char }

fun Font.getCharacterOrDefault(char: Char) = getCharacter(char) ?: this.defaultCharacter
