package org.saar.example.renderer;

import org.saar.core.model.Model;
import org.saar.core.model.Models;
import org.saar.core.model.vertex.*;
import org.saar.core.renderer.basic.BasicRenderer;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

public class RendererExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final BasicRenderer renderer = new BasicRenderer(null);

        final ModelIndices indices = new ModelIndices(0, 1, 3, 2);
        final ModelVertices<MyVertex> vertices = new ModelVertices<>(
                new MyVertex(Vector2.of(-1.0f, -1.0f), Vector3.of(+0.0f, +0.0f, +0.5f)),
                new MyVertex(Vector2.of(-1.0f, +1.0f), Vector3.of(+0.0f, +1.0f, +0.5f)),
                new MyVertex(Vector2.of(+1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +0.5f)),
                new MyVertex(Vector2.of(+1.0f, -1.0f), Vector3.of(+1.0f, +0.0f, +0.5f)));
        final Model model = Models.elementsModel(RenderMode.TRIANGLE_STRIP, modelBuffer, indices, vertices);

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttributes("in_position", "in_colour");

        shadersProgram.bind();

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            model.draw();
            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                final IFbo temp = fbo;
                fbo = createFbo(window.getWidth(), window.getHeight());
                temp.delete();
            }
            GlUtils.clear(GlBuffer.COLOUR);
        }
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height);
        final RenderBufferAttachmentMS attachment = new RenderBufferAttachmentMS(
                AttachmentType.COLOUR, 0, RenderBuffer.create(), FormatType.BGRA, 16);
        fbo.addAttachment(attachment);
        return fbo;
    }

}
