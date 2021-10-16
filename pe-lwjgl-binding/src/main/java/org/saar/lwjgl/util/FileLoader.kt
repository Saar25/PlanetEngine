package org.saar.lwjgl.util

import java.io.FileNotFoundException
import java.util.*

object FileLoader {

    @JvmStatic
    fun loadTextFile(fileName: String): String {
        val resource = FileLoader::class.java.getResourceAsStream(fileName)
            ?: throw FileNotFoundException("$fileName not found")

        return resource.use { Scanner(resource, "UTF-8").useDelimiter("\\A").next() }
    }
}