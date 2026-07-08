package org.saar.rhi.shader

import java.io.FileNotFoundException
import java.util.*

object ShaderModuleLoader {

    fun loadSource(file: String): String {
        val code = loadTextFile(file)
        return preProcessCode(code)
    }

    private fun loadTextFile(fileName: String): String {
        val resource = ShaderModuleLoader::class.java.getResourceAsStream(fileName)
            ?: throw FileNotFoundException("$fileName not found")

        return resource.use { Scanner(resource, "UTF-8").useDelimiter("\\A").next() }
    }

    private fun loadSource(file: String, required: Boolean, vararg included: String?): String {
        try {
            val code: String = loadTextFile(file)
            return preProcessCode(code, *included)
        } catch (e: Exception) {
            if (required) {
                throw ShaderLoaderException("Shader not found, path: $file", e)
            } else {
                return ""
            }
        }
    }

    private fun preProcessCode(code: String, vararg included: String?): String {
        val codeBuilder = java.lang.StringBuilder()
        val toAppend = java.lang.StringBuilder()

        for (line in code.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            if (line.startsWith("#include ")) {
                val include: String = findMacroStringValue(line)
                checkCircularDependency(include, *included)

                val newIncluded = arrayOfNulls<String>(included.size + 1)
                newIncluded[included.size] = include

                codeBuilder.append(loadSource("$include.struct.glsl", false, *newIncluded)).append('\n')
                codeBuilder.append(loadSource("$include.header.glsl", false, *newIncluded)).append('\n')
                toAppend.append(loadSource("$include.source.glsl", true, *newIncluded)).append('\n')
            } else {
                codeBuilder.append(line).append('\n')
            }
        }

        return codeBuilder.append(toAppend).append('\n').toString()
    }

    private fun findMacroStringValue(line: String): String {
        val beginIndex = line.indexOf('"') + 1
        val endIndex = line.lastIndexOf('"')
        return line.substring(beginIndex, endIndex)
    }

    private fun checkCircularDependency(file: String, vararg files: String?) {
        if (files.contains(file)) {
            throw ShaderLoaderException(
                "Circular dependency: ${files.joinToString(" -> ")} -> $file"
            )
        }
    }
}