package org.saar.lwjgl.opengl.constants;

public enum DepthFormatType {

    COMPONENT16(InternalFormat.DEPTH16),
    COMPONENT24(InternalFormat.DEPTH24),
    COMPONENT32(InternalFormat.DEPTH32),
    ;

    private final InternalFormat value;

    DepthFormatType(InternalFormat value) {
        this.value = value;
    }

    public InternalFormat get() {
        return this.value;
    }

}
