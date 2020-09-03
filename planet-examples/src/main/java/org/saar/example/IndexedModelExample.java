package org.saar.example;

import org.saar.core.common.simple.SimpleBufferWriter;
import org.saar.core.common.simple.SimpleVertex;
import org.saar.core.model.Model;
import org.saar.core.model.Models;
import org.saar.core.model.vertex.ModelAttribute;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentMS;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

import java.util.Arrays;
import java.util.List;

public class IndexedModelExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static AttachmentMS attachment;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        attachment = RenderBufferAttachmentMS.ofColour(0, FormatType.BGRA, 16);

        final ModelIndices indices = new ModelIndices(0, 1, 3, 2);
        final List<SimpleVertex> vertices = Arrays.asList(
                new SimpleVertex(-1.0f, -1.0f, +0.0f, +0.0f, +0.5f),
                new SimpleVertex(-1.0f, +1.0f, +0.0f, +1.0f, +0.5f),
                new SimpleVertex(+1.0f, +1.0f, +1.0f, +1.0f, +0.5f),
                new SimpleVertex(+1.0f, -1.0f, +1.0f, +0.0f, +0.5f));
        final SimpleBufferWriter writer = new SimpleBufferWriter(
                new ModelAttribute(2, true, DataType.FLOAT),
                new ModelAttribute(3, true, DataType.FLOAT));
        final Model model = Models.elementsModel(RenderMode.TRIANGLE_STRIP, writer, indices, vertices);

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttributes("in_position", "in_colour");

        shadersProgram.bind();

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            GlUtils.clear(GlBuffer.COLOUR);
            model.draw();
            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                fbo.delete();
                fbo = createFbo(window.getWidth(), window.getHeight());
            }
        }

        fbo.delete();
        model.delete();
        attachment.delete();
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height);
        fbo.setDrawAttachments(attachment);
        fbo.setReadAttachment(attachment);
        fbo.addAttachment(attachment);
        return fbo;
    }

}
