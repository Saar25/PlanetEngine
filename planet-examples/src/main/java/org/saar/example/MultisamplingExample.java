package org.saar.example;

import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class MultisamplingExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
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

        final MultisampledFbo fbo = new MultisampledFbo(WIDTH, HEIGHT);
        final RenderBufferAttachmentMS attachment = new RenderBufferAttachmentMS(
                AttachmentType.COLOUR, 0, RenderBuffer.create(), FormatType.BGRA, 16);
        fbo.addAttachment(attachment);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            GlRendering.drawArrays(RenderMode.TRIANGLES, 0, 3);
            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
        }
    }

}
