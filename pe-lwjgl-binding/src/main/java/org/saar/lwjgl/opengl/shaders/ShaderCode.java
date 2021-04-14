package org.saar.lwjgl.opengl.shaders;

public class ShaderCode {

    private final String code;

    private ShaderCode(String code) {
        this.code = code;
    }

    public static ShaderCode define(String name, String value) {
        return new ShaderCode("#define " + name + " " + value);
    }

    public static ShaderCode loadSource(String file) throws Exception {
        final String code = ShaderCodeLoader.loadSource(file);
        return new ShaderCode(code);
    }

    public String getCode() {
        return this.code;
    }
}
