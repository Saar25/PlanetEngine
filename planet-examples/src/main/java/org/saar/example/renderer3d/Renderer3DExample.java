package org.saar.example.renderer3d;

import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.renderer.r3d.RenderNode3D;
import org.saar.core.renderer.r3d.Renderer3D;
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
import org.saar.maths.objects.Transform;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

public class Renderer3DExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final int[] a = new int[36];
        for (int i = 0; i < a.length; i++) a[i] = i;
        final ModelIndices indices = new ModelIndices(a);

        final ModelVertices<MyVertex> vertices = new ModelVertices<>(
                new MyVertex(Vector3.of(-1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, -1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, -1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, -1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, -1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(-1.0f, +1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)),
                new MyVertex(Vector3.of(+1.0f, -1.0f, +1.0f), Vector3.of(+1.0f, +1.0f, +1.0f)));
        final MyNode node = new MyNode(vertices, indices, new Transform());

        final Projection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 100);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(10, 10, 10));
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final RenderNode3D renderNode = new RenderNode3D(node);
        final Renderer3D renderer = new Renderer3D(camera, renderNode);

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            GlUtils.clear(GlBuffer.COLOUR);

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
