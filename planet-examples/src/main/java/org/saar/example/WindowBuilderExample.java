package org.saar.example;

import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.glfw.window.hint.WindowHintResizeable;
import org.saar.lwjgl.glfw.window.hint.WindowHintTransparentFramebuffer;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class WindowBuilderExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = Window.builder("Lwjgl", WIDTH, HEIGHT, true)
                .hint(new WindowHintTransparentFramebuffer(true))
                .hint(new WindowHintResizeable(false))
                .build();

        final Vao vao = Vao.create();
        final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);
        vbo.allocateFloat(18);
        vbo.storeFloat(0, new float[]{
                -0.5f, -0.5f, 1.0f, 1.0f, 1.0f, 0.0f,
                +0.0f, +0.5f, 1.0f, 1.0f, 1.0f, 0.0f,
                +0.5f, -0.5f, 1.0f, 1.0f, 1.0f, 0.0f});
        vao.loadVbo(vbo,
                Attribute.of(0, 2, DataType.FLOAT, false),
                Attribute.of(1, 3, DataType.FLOAT, false),
                Attribute.of(2, 1, DataType.FLOAT, false));
        vbo.delete();

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttribute(0, "in_position");

        shadersProgram.bind();

        vao.bind();
        vao.enableAttributes();

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlRendering.drawArrays(RenderMode.TRIANGLES, 0, 3);

            window.update(true);
            window.pollEvents();
        }

        vao.delete();
        window.destroy();
    }

}
