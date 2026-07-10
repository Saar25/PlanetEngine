package org.saar.rhi.shader

import java.io.FileNotFoundException
import java.util.*

object ShaderModuleLoader {

    fun loadSource(file: String): String {
        val code = loadTextFile(file)
        return preProcessCode(code, true, emptyList())
    }

    private fun loadTextFile(fileName: String): String {
        val resource = ShaderModuleLoader::class.java.getResourceAsStream(fileName)
            ?: throw FileNotFoundException("$fileName not found")

        return resource.use { Scanner(resource, "UTF-8").useDelimiter("\\A").next() }
    }

    private fun loadSource(file: String, required: Boolean, included: List<String>): String {
        try {
            val code: String = loadTextFile(file)
            return preProcessCode(code, false, included)
        } catch (e: Exception) {
            if (required) {
                throw ShaderLoaderException("Shader not found, path: $file", e)
            } else {
                return ""
            }
        }
    }

    private fun preProcessCode(code: String, root: Boolean, included: List<String>): String {
        val versionBuilder = StringBuilder()
        val headers = StringBuilder()
        val lines = StringBuilder()
        val sources = StringBuilder()

        for (line in code.split('\n').filter { it.isNotBlank() }) {
            val trimmed = line.trimStart()
            if (trimmed.startsWith("#version")) {
                if (root) {
                    versionBuilder.clear().append(trimmed).append('\n')
                }
            } else if (line.startsWith("#include")) {
                val include = findIncluded(line)
                checkCircularDependency(include, included)

                val newIncluded = included + include
                headers.append(loadSource("$include.header.glsl", false, newIncluded)).append('\n')
                sources.append(loadSource("$include.source.glsl", true, newIncluded)).append('\n')
            } else {
                lines.append(line).append('\n')
            }
        }

        return versionBuilder.append(headers).append(lines).append(sources).toString()
    }

    private fun findIncluded(line: String): String {
        val beginIndex = line.indexOf('"') + 1
        val endIndex = line.lastIndexOf('"')
        return line.substring(beginIndex, endIndex)
    }

    private fun checkCircularDependency(file: String, files: List<String>) {
        if (files.contains(file)) {
            throw ShaderLoaderException(
                "Circular dependency: ${files.joinToString(" -> ")} -> $file"
            )
        }
    }
}