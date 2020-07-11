package org.saar.example;

import org.saar.core.model.Model;
import org.saar.core.model.Models;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.model.vertex.ModelVerticesAttribute;
import org.saar.core.model.vertex.ModelVerticesSingleVbo;
import org.saar.core.model.vertex.SimpleVertex;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public class ModelExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final ModelVertices modelVertices = new ModelVerticesSingleVbo(
                new ModelVerticesAttribute(2, true, DataType.FLOAT),
                new ModelVerticesAttribute(3, true, DataType.FLOAT));
        final Model model = Models.arraysModel(RenderMode.TRIANGLES, modelVertices,
                new SimpleVertex(-0.5f, -0.5f, +0.0f, +0.0f, +0.5f),
                new SimpleVertex(+0.0f, +0.5f, +0.5f, +1.0f, +0.5f),
                new SimpleVertex(+0.5f, -0.5f, +1.0f, +0.0f, +0.5f));

        final ShadersProgram<Object> shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttribute(0, "in_position");
        shadersProgram.bindAttribute(1, "in_colour");

        shadersProgram.bind();

        final MultisampledFbo fbo = new MultisampledFbo(WIDTH, HEIGHT);
        final RenderBufferAttachmentMS attachment = new RenderBufferAttachmentMS(
                AttachmentType.COLOUR, 0, RenderBuffer.create(), FormatType.BGRA, 16);
        fbo.addAttachment(attachment);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            model.draw();
            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
        }
    }

}
