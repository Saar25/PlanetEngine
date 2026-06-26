package org.saar.example;

import org.saar.core.common.r2d.Model2D;
import org.saar.core.common.r2d.R2D;
import org.saar.core.common.r2d.Renderer2D;
import org.saar.core.common.r2d.Vertex2D;
import org.saar.core.mesh.Mesh;
import org.saar.core.renderer.RenderContext;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.FboBlitFilter;
import org.saar.lwjgl.opengl.fbo.WindowFbo;
import org.saar.lwjgl.opengl.fbo.attachment.Attachment;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.MultisampledAllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex;
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

        final Mesh mesh = R2D.mesh(vertices, indices);
        final Model2D model = new Model2D(mesh);
        final Renderer2D renderer = Renderer2D.INSTANCE;

        final AllocationStrategy allocation = new MultisampledAllocationStrategy(4);
        final AttachmentBuffer buffer = new RenderBufferAttachmentBuffer(InternalFormat.RGBA8);
        final Attachment attachment = new Attachment(buffer, allocation);
        final AttachmentIndex attachmentIndex = ColorAttachmentIndex.at(0);
        final RenderTarget target = new IndexRenderTarget(attachmentIndex);
        final Fbo fbo = Fbo.create(WIDTH, HEIGHT);

        attachment.allocate(WIDTH, HEIGHT);
        fbo.addAttachment(attachmentIndex, attachment);
        fbo.setReadTarget(target);
        fbo.setDrawTarget(target);
        fbo.ensureStatus();

        int[] dimensions = {WIDTH, HEIGHT};

        window.addResizeListener(e -> {
            int width = e.getWidth().getAfter();
            int height = e.getHeight().getAfter();
            fbo.bind();
            dimensions[0] = width;
            dimensions[1] = height;
            attachment.allocate(width, height);
        });

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            GlUtils.setViewport(0, 0, dimensions[0], dimensions[1]);

            fbo.bind();

            GlUtils.clear(GlBuffer.COLOR);

            renderer.render(new RenderContext(), model);

            WindowFbo.getInstance().bindAsDraw();
            GlUtils.clear(GlBuffer.COLOR);
            fbo.blitFramebuffer(
                    dimensions[0], dimensions[1],
                    FboBlitFilter.LINEAR, GlBuffer.COLOR);

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
