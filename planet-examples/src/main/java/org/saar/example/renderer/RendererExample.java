package org.saar.example.renderer;

import org.joml.Vector2f;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.renderer.basic.BasicRenderNode;
import org.saar.core.renderer.basic.BasicRenderer;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

public class RendererExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final float a = 0.7f, b = 0.3f;
        final ModelIndices indices = new ModelIndices(0, 1, 2, 0, 2, 3);
        final ModelVertices<MyVertex> vertices = new ModelVertices<>(
                new MyVertex(Vector2.of(-a, -a + .1f), Vector3.of(+0.0f, +0.0f, +0.5f)),
                new MyVertex(Vector2.of(-a, +a), Vector3.of(+0.0f, +1.0f, +0.5f)),
                new MyVertex(Vector2.of(+a, +a), Vector3.of(+1.0f, +1.0f, +0.5f)),
                new MyVertex(Vector2.of(+a, -a), Vector3.of(+1.0f, +0.0f, +0.5f)));
        final MyMesh mesh = new MyMesh(vertices, indices);
        final MyNode node = new MyNode();

        final BasicRenderNode renderNode = new BasicRenderNode(mesh, node);
        final BasicRenderer renderer = new BasicRenderer(renderNode);

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            GlUtils.clear(GlBuffer.COLOUR);

            ((Vector2f) mesh.getVertices().getVertices().get(0).getPosition2f()).x += .001f;
            ((Vector2f) mesh.getVertices().getVertices().get(0).getPosition2f()).y += .001f;
            renderNode.update();
            renderer.render();

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                final IFbo temp = fbo;
                fbo = createFbo(window.getWidth(), window.getHeight());
                temp.delete();
            }
        }

        renderer.delete();
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height);
        final RenderBufferAttachmentMS attachment = new RenderBufferAttachmentMS(
                AttachmentType.COLOUR, 0, RenderBuffer.create(), FormatType.BGRA, 16);
        fbo.addAttachment(attachment);
        return fbo;
    }

}
