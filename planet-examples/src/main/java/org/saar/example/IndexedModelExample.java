package org.saar.example;

import org.saar.core.model.Model;
import org.saar.core.model.Models;
import org.saar.core.model.vertex.SimpleVertex;
import org.saar.core.model.vertex.VertexBufferAttribute;
import org.saar.core.model.vertex.VerticesBuffer;
import org.saar.core.model.vertex.VerticesBufferSingleVbo;
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

public class IndexedModelExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final float x = 1.0f;
        final int[] indices = {0, 1, 3, 2};
        final VerticesBuffer verticesBuffer = new VerticesBufferSingleVbo(
                new VertexBufferAttribute(2, true, DataType.FLOAT),
                new VertexBufferAttribute(3, true, DataType.FLOAT));
        final Model model = Models.elementsModel(RenderMode.TRIANGLE_STRIP, verticesBuffer, indices,
                new SimpleVertex(-x, -x, +0.0f, +0.0f, +0.5f),
                new SimpleVertex(-x, +x, +0.0f, +1.0f, +0.5f),
                new SimpleVertex(+x, +x, +1.0f, +1.0f, +0.5f),
                new SimpleVertex(+x, -x, +1.0f, +0.0f, +0.5f));

        final ShadersProgram<Object> shadersProgram = ShadersProgram.create(
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
