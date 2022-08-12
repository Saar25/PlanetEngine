package org.saar.example;

import org.saar.core.common.r2d.*;
import org.saar.core.renderer.RenderContext;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.MultisampledAllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget;
import org.saar.lwjgl.opengl.fbo.rendertarget.RenderTarget;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

public class RendererExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final float s = 0.7f;
        final int[] indices = {0, 1, 2, 0, 2, 3};
        final Vertex2D[] vertices = {
                R2D.vertex(Vector2.of(-s, -s), Vector3.of(+0.0f, +0.0f, +0.5f)),
                R2D.vertex(Vector2.of(-s, +s), Vector3.of(+0.0f, +1.0f, +0.5f)),
                R2D.vertex(Vector2.of(+s, +s), Vector3.of(+1.0f, +1.0f, +0.5f)),
                R2D.vertex(Vector2.of(+s, -s), Vector3.of(+1.0f, +0.0f, +0.5f))};

        final Mesh2D mesh = R2D.mesh(vertices, indices);
        final Model2D model = new Model2D(mesh);
        final Renderer2D renderer = Renderer2D.INSTANCE;

        final AllocationStrategy allocation = new MultisampledAllocationStrategy(4);
        final AttachmentBuffer buffer = new RenderBufferAttachmentBuffer(InternalFormat.RGBA8);
        final ColourAttachment attachment = new ColourAttachment(0, buffer, allocation);
        final RenderTarget target = new IndexRenderTarget(attachment.getIndex());
        final Fbo fbo = Fbo.create(WIDTH, HEIGHT);

        fbo.addAttachment(attachment);
        fbo.setReadTarget(target);
        fbo.setDrawTarget(target);
        fbo.ensureStatus();

        window.addResizeListener(e -> {
            fbo.bind();
            fbo.resize(e.getWidth().getAfter(),
                    e.getHeight().getAfter());
            attachment.init(fbo);
        });

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            fbo.bind();

            GlUtils.clear(GlBuffer.COLOUR);

            renderer.render(new RenderContext(null), model);

            fbo.blitToScreen();

            window.swapBuffers();
            window.pollEvents();
        }

        renderer.delete();
        fbo.delete();
        model.delete();
        attachment.delete();
        window.destroy();
    }
}
