package org.saar.example;

import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class Example {

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", 700, 500, true);
        window.init();

        final Vao vao = Vao.create();
        final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);
        vbo.allocateFloat(6);
        vbo.storeData(0, new float[]{
                -0.5f, -0.5f,
                +0.0f, +0.5f,
                +0.5f, -0.5f});
        vao.loadDataBuffer(vbo, Attribute.of(0, 2, DataType.FLOAT, false));

        final ShadersProgram<Vao> shadersProgram = ShadersProgram.create(
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
    }

}
