package org.saar.lwjgl.opengl.shaders;

import org.lwjgl.opengl.GL20;

public class Shader {

    public final int id;
    private final ShaderType type;
    private final String[] code;

    public Shader(int id, ShaderType type, String... code) {
        this.id = id;
        this.type = type;
        this.code = code;
    }

    /**
     * Creates a shader of the given type, with the given file that contains the code
     *
     * @param sources the sources code file
     * @param type    the shader type
     * @return a vertex shader
     */
    public static Shader of(ShaderType type, GlslVersion version, ShaderCode... sources) {
        final int id = GL20.glCreateShader(type.get());
        return new Shader(id, type, getSources(version, sources));
    }

    private static String[] getSources(GlslVersion version, ShaderCode... sources) {
        final String[] codes = new String[sources.length + 1];
        codes[0] = version.toString() + '\n';
        for (int i = 0; i < sources.length; i++) {
            codes[i + 1] = sources[i].getCode() + '\n';
        }
        return codes;
    }

    /**
     * Creates a vertex shader with the given file that contains the code
     *
     * @param version the shader version
     * @param sources the sources code file
     * @return a vertex shader
     */
    public static Shader createVertex(GlslVersion version, ShaderCode... sources) {
        return Shader.of(ShaderType.VERTEX, version, sources);
    }

    public static Shader createVertex(String source) throws Exception {
        return Shader.createVertex(GlslVersion.NONE, ShaderCode.loadSource(source));
    }

    /**
     * Creates a fragment shader with the given file that contains the code
     *
     * @param version the shader version
     * @param sources the sources code file
     * @return a fragment shader
     */
    public static Shader createFragment(GlslVersion version, ShaderCode... sources) {
        return Shader.of(ShaderType.FRAGMENT, version, sources);
    }

    public static Shader createFragment(String source) throws Exception {
        return Shader.of(ShaderType.FRAGMENT, GlslVersion.NONE, ShaderCode.loadSource(source));
    }

    /**
     * Creates a geometry shader with the given file that contains the code
     *
     * @param sources the sources code file
     * @return a geometry shader
     */
    public static Shader createGeometry(GlslVersion version, ShaderCode... sources) {
        return Shader.of(ShaderType.GEOMETRY, version, sources);
    }

    /**
     * Creates a tessellation control shader with the given file that contains the code
     *
     * @param sources the sources code file
     * @return a tessellation control shader
     */
    public static Shader createTessControl(GlslVersion version, ShaderCode... sources) {
        return Shader.of(ShaderType.TESS_CONTROL, version, sources);
    }

    /**
     * Creates a tessellation evaluation shader with the given file that contains the code
     *
     * @param sources the sources code file
     * @return a tessellation evaluation shader
     */
    public static Shader createTessEvaluation(GlslVersion version, ShaderCode... sources) {
        return Shader.of(ShaderType.TESS_EVALUATION, version, sources);
    }

    /**
     * Initializes the shader, compiles, and checking for error in the shader code
     *
     * @throws ShaderCompileException In case could not compile the shader code
     */
    public void init() throws ShaderCompileException {
        GL20.glShaderSource(id, code);
        GL20.glCompileShader(id);
        if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == 0) {
            final String infoLog = GL20.glGetShaderInfoLog(id, 1024);
            throw new ShaderCompileException("Shader type: " + type +
                    ", Error compiling Shader code:\n" + infoLog);
        }
    }

    /**
     * Attaches the shader to the given program
     *
     * @param programId the shader program
     */
    public void attach(int programId) {
        GL20.glAttachShader(programId, id);
    }

    /**
     * Detaches the shader from the given program
     *
     * @param programId the shader program
     */
    public void detach(int programId) {
        GL20.glDetachShader(programId, id);
    }

    /**
     * Deletes the shader
     */
    public void delete() {
        GL20.glDeleteShader(id);
    }
}