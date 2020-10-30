package org.saar.example.obj;

import org.saar.core.camera.Camera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjRenderer;
import org.saar.core.renderer.RenderContextBase;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;

public class ObjRendererExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static ColourAttachment colorAttachment;
    private static DepthAttachment depthAttachment;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, false);
        window.init();

        colorAttachment = ColourAttachment.withRenderBuffer(0, InternalFormat.RGBA8);
        depthAttachment = DepthAttachment.withRenderBuffer(DepthFormatType.COMPONENT24);

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        ObjModel model = null;
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            model = new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        final ObjRenderer renderer = new ObjRenderer(model);

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            fbo.bind();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            ExamplesUtils.move(camera, keyboard);
            renderer.render(new RenderContextBase(camera));

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                fbo.delete();
                fbo = createFbo(window.getWidth(), window.getHeight());
            }

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        renderer.delete();
        fbo.delete();
        colorAttachment.delete();
        window.destroy();
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height, 16);
        fbo.setDrawAttachments(colorAttachment);
        fbo.setReadAttachment(colorAttachment);
        fbo.addAttachment(colorAttachment);
        fbo.addAttachment(depthAttachment);
        fbo.ensureStatus();
        return fbo;
    }

}
