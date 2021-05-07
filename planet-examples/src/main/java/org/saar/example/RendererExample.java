package org.saar.example;

import org.saar.core.common.r2d.*;
import org.saar.core.renderer.RenderContextBase;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
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

        final Mesh2D mesh = Mesh2D.load(vertices, indices);
        final Model2D model = new Model2D(mesh);
        final Renderer2D renderer = Renderer2D.INSTANCE;

        final ColourAttachment attachment = ColourAttachment.withRenderBuffer(0, ColourFormatType.RGBA8);
        final MultisampledFbo fbo = new MultisampledFbo(WIDTH, HEIGHT, 16);

        fbo.addAttachment(attachment);
        fbo.setDrawAttachments(attachment);
        fbo.setReadAttachment(attachment);
        fbo.ensureStatus();

        window.addResizeListener(e -> {
            fbo.bind();
            fbo.resize(e.getWidth().getAfter(),
                    e.getHeight().getAfter());
            attachment.initMS(fbo, 16);
        });

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            fbo.bind();

            GlUtils.clear(GlBuffer.COLOUR);

            renderer.render(new RenderContextBase(null), model);

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
        }

        renderer.delete();
        fbo.delete();
        model.delete();
        attachment.delete();
        window.destroy();
    }
}
