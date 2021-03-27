package org.saar.lwjgl.opengl.constants;

public enum DepthStencilFormatType {

    DEPTH24_STENCIL8(InternalFormat.DEPTH24_STENCIL8),
    DEPTH32F_STENCIL8(InternalFormat.DEPTH32F_STENCIL8),
    ;

    private final InternalFormat value;

    DepthStencilFormatType(InternalFormat value) {
        this.value = value;
    }

    public InternalFormat get() {
        return this.value;
    }

}
