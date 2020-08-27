package org.saar.example;

import org.saar.core.common.simple.SimpleBufferWriter;
import org.saar.core.common.simple.SimpleVertex;
import org.saar.core.model.Model;
import org.saar.core.model.Models;
import org.saar.core.model.vertex.ModelAttribute;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public class ModelExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final ModelVertices<SimpleVertex> vertices = new ModelVertices<>(
                new SimpleVertex(-0.5f, -0.5f, +0.0f, +0.0f, +0.5f),
                new SimpleVertex(+0.0f, +0.5f, +0.5f, +1.0f, +0.5f),
                new SimpleVertex(+0.5f, -0.5f, +1.0f, +0.0f, +0.5f));
        final SimpleBufferWriter writer = new SimpleBufferWriter(
                new ModelAttribute(2, true, DataType.FLOAT),
                new ModelAttribute(3, true, DataType.FLOAT));
        final Model model = Models.arraysModel(RenderMode.TRIANGLE_STRIP, writer, vertices);

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttributes("in_position", "in_colour");

        shadersProgram.bind();

        final MultisampledFbo fbo = new MultisampledFbo(WIDTH, HEIGHT);
        final RenderBufferAttachmentMS attachment = RenderBufferAttachmentMS.ofColour(0, FormatType.BGRA, 16);
        fbo.addAttachment(attachment);
        fbo.setReadAttachment(attachment);
        fbo.setDrawAttachments(attachment);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            model.draw();
            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
        }

        fbo.delete();
        model.delete();
        attachment.delete();
    }

}
