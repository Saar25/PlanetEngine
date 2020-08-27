package org.saar.example.renderer;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.renderer.r2d.RenderNode2D;
import org.saar.core.renderer.r2d.Renderer2D;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

public class RendererExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static RenderBufferAttachmentMS attachment;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        attachment = RenderBufferAttachmentMS.ofColour(0, FormatType.BGRA, 16);

        final float a = 0.7f, b = 0.3f;
        final ModelIndices indices = new ModelIndices(0, 1, 2, 0, 2, 3);
        final ModelVertices<MyVertex> vertices = new ModelVertices<>(
                new MyVertex(Vector2.of(-a, -a + .1f), Vector3.of(+0.0f, +0.0f, +0.5f)),
                new MyVertex(Vector2.of(-a, +a), Vector3.of(+0.0f, +1.0f, +0.5f)),
                new MyVertex(Vector2.of(+a, +a), Vector3.of(+1.0f, +1.0f, +0.5f)),
                new MyVertex(Vector2.of(+a, -a), Vector3.of(+1.0f, +0.0f, +0.5f)));
        final MyMesh mesh = new MyMesh(vertices, indices);
        final MyNode node = new MyNode();

        final RenderNode2D renderNode = new RenderNode2D(mesh, node);
        final Renderer2D renderer = new Renderer2D(renderNode);

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            GlUtils.clear(GlBuffer.COLOUR);

//            ((Vector2f) mesh.getVertices().getVertices().get(0).getPosition2f()).x += .001f;
//            ((Vector2f) mesh.getVertices().getVertices().get(0).getPosition2f()).y += .001f;
//            renderNode.update();
            renderer.render();

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                fbo.delete();
                fbo = createFbo(window.getWidth(), window.getHeight());
            }
        }

        renderer.delete();
        fbo.delete();
        renderNode.getModel().delete();
        attachment.delete();
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height);
        fbo.setDrawAttachments(attachment);
        fbo.setReadAttachment(attachment);
        fbo.addAttachment(attachment);
        fbo.ensureStatus();
        return fbo;
    }

}
