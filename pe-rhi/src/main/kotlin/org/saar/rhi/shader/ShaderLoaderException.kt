package org.saar.rhi.shader

class ShaderLoaderException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}
