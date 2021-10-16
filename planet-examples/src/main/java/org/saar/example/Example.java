package org.saar.example;

import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class Example {

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", 700, 500, true);

        final Vao vao = Vao.create();
        final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);
        vbo.allocateFloat(18);
        vbo.storeFloat(0, new float[]{
                -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f,
                +0.0f, +0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
                +0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f});
        vao.loadVbo(vbo,
                Attributes.of(0, 2, DataType.FLOAT, false),
                Attributes.of(1, 3, DataType.FLOAT, false),
                Attributes.of(2, 1, DataType.FLOAT, false));

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttribute(0, "in_position");

        shadersProgram.bind();

        vao.bind();
        vao.enableAttributes();

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            GlRendering.drawArrays(RenderMode.TRIANGLES, 0, 3);

            window.update(true);
            window.pollEvents();
        }

        window.destroy();
    }

}
