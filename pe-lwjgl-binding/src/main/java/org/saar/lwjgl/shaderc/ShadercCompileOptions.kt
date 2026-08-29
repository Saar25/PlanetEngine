package org.saar.lwjgl.shaderc

import org.lwjgl.system.Callback
import org.lwjgl.util.shaderc.Shaderc

class ShadercCompileOptions private constructor(val handle: Long) : AutoCloseable {

    private val retainedCallbacks = mutableListOf<Callback>()

    companion object {
        fun create(): ShadercCompileOptions {
            val handle = Shaderc.shaderc_compile_options_initialize()

            return ShadercCompileOptions(handle)
        }
    }

    fun addMacroDefinition(name: String, value: String? = null): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_add_macro_definition(this.handle, name, value)
    }

    fun setSourceLanguage(language: ShadercSourceLanguage): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_source_language(this.handle, language.value)
    }

    fun setOptimizationLevel(level: ShadercOptimizationLevel): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_optimization_level(this.handle, level.value)
    }

    fun setForcedVersionProfile(version: Int, profile: ShadercProfile): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_forced_version_profile(this.handle, version, profile.value)
    }

    fun generateDebugInfo(): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_generate_debug_info(this.handle)
    }

    fun setWarningsAsErrors(): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_warnings_as_errors(this.handle)
    }

    fun suppressWarnings(): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_suppress_warnings(this.handle)
    }

    fun setTargetEnv(env: ShadercTargetEnv, version: ShadercEnvVersion): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_target_env(this.handle, env.value, version.value)
    }

    fun setTargetSpirv(version: ShadercSpirvVersion): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_target_spirv(this.handle, version.value)
    }

    fun setIncludeCallbacks(
        resolver: ShadercIncludeResolveCallback?,
        release: ShadercIncludeReleaseCallback = { _, includeResult -> includeResult.close() },
    ): ShadercCompileOptions = apply {
        Shaderc.shaderc_compile_options_set_include_callbacks(
            this.handle,
            resolver?.toShaderc(),
            release.toShaderc(),
            0L
        )
    }

    override fun close() = Shaderc.shaderc_compile_options_release(this.handle)
}
