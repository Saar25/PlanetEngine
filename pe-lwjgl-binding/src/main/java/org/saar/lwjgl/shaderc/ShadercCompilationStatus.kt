package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc

enum class ShadercCompilationStatus(val value: Int) {
    SUCCESS(Shaderc.shaderc_compilation_status_success),
    INVALID_STAGE(Shaderc.shaderc_compilation_status_invalid_stage),
    COMPILATION_ERROR(Shaderc.shaderc_compilation_status_compilation_error),
    INTERNAL_ERROR(Shaderc.shaderc_compilation_status_internal_error),
    NULL_RESULT_OBJECT(Shaderc.shaderc_compilation_status_null_result_object),
    INVALID_ASSEMBLY(Shaderc.shaderc_compilation_status_invalid_assembly),
    VALIDATION_ERROR(Shaderc.shaderc_compilation_status_validation_error),
    TRANSFORMATION_ERROR(Shaderc.shaderc_compilation_status_transformation_error),
    CONFIGURATION_ERROR(Shaderc.shaderc_compilation_status_configuration_error);

    val isSuccess: Boolean get() = this == SUCCESS

    companion object {
        fun of(value: Int): ShadercCompilationStatus =
            entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Unknown shaderc compilation status: $value")
    }
}
