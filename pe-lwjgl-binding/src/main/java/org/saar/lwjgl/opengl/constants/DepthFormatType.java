package org.saar.lwjgl.opengl.constants;

public enum DepthFormatType {

    COMPONENT16(InternalFormat.DEPTH16),
    COMPONENT24(InternalFormat.DEPTH24),
    COMPONENT32(InternalFormat.DEPTH32),
    COMPONENT24_STENCIL8(InternalFormat.DEPTH24_STENCIL8),
    COMPONENT32F_STENCIL8(InternalFormat.DEPTH32F_STENCIL8),
    ;

    private final InternalFormat value;

    DepthFormatType(InternalFormat value) {
        this.value = value;
    }

    public InternalFormat get() {
        return this.value;
    }

}
