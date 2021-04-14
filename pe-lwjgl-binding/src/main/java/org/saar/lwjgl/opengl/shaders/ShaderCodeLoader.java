package org.saar.lwjgl.opengl.shaders;

import org.saar.utils.file.TextFileLoader;

import java.util.Arrays;

public final class ShaderCodeLoader {

    private ShaderCodeLoader() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static String loadSource(String file) throws Exception {
        final String code = TextFileLoader.loadResource(file);
        return preProcessCode(code);
    }

    public static String loadSource(String file, boolean required, String... included) throws ShaderLoaderException {
        try {
            final String code = TextFileLoader.loadResource(file);
            return preProcessCode(code, included);
        } catch (Exception e) {
            if (required) {
                throw new ShaderLoaderException(
                        "Shader not found, path: " + file, e);
            } else {
                return "";
            }
        }
    }

    private static String preProcessCode(String code, String... included) throws ShaderLoaderException {
        final StringBuilder codeBuilder = new StringBuilder();
        final StringBuilder toAppend = new StringBuilder();

        for (String line : code.split("\n")) {
            if (line.startsWith("#include ")) {
                final String include = findMacroStringValue(line);
                checkCircularDependency(include, included);

                final String[] newIncluded = Arrays.copyOf(included, included.length + 1);
                newIncluded[included.length] = include;

                codeBuilder.append(loadSource(include + ".struct.glsl", false, newIncluded));
                codeBuilder.append(loadSource(include + ".header.glsl", false, newIncluded));
                toAppend.append(loadSource(include + ".source.glsl", true, newIncluded));
            } else {
                codeBuilder.append(line);
            }
        }

        return codeBuilder.append(toAppend).toString();
    }

    private static String findMacroStringValue(String line) {
        final int beginIndex = line.indexOf('"') + 1;
        final int endIndex = line.lastIndexOf('"');
        return line.substring(beginIndex, endIndex);
    }

    private static void checkCircularDependency(String file, String... files) {
        if (Arrays.asList(files).contains(file)) {
            throw new ShaderLoaderException("Circular dependency: " +
                    String.join(" -> ", files) + " -> " + file);
        }
    }
}
