package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc
import java.nio.ByteBuffer

class ShadercCompilationResult internal constructor(val handle: Long) : AutoCloseable {

    val status: ShadercCompilationStatus
        get() = ShadercCompilationStatus.of(Shaderc.shaderc_result_get_compilation_status(handle))

    val isSuccess: Boolean
        get() = status.isSuccess

    val length: Long
        get() = Shaderc.shaderc_result_get_length(handle)

    val bytes: ByteBuffer?
        get() = Shaderc.shaderc_result_get_bytes(handle)

    val errorMessage: String?
        get() = Shaderc.shaderc_result_get_error_message(handle)

    val numWarnings: Long
        get() = Shaderc.shaderc_result_get_num_warnings(handle)

    val numErrors: Long
        get() = Shaderc.shaderc_result_get_num_errors(handle)

    override fun close() = Shaderc.shaderc_result_release(handle)
}
