package org.saar.example.obj;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjRenderer;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.screen.MainScreen;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
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
    private static MultisampledFbo fbo;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        colorAttachment = ColourAttachment.withRenderBuffer(0, ColourFormatType.RGBA8);
        depthAttachment = DepthAttachment.withRenderBuffer(DepthFormatType.COMPONENT24);

        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);
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

        final ObjRenderer renderer = new ObjRenderer();

        fbo = createFbo(WIDTH, HEIGHT);

        window.addResizeListener(e -> {
            fbo.delete();
            fbo = createFbo(window.getWidth(), window.getHeight());
        });

        final Keyboard keyboard = window.getKeyboard();
        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            fbo.bind();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            ExamplesUtils.move(camera, keyboard);
            renderer.render(new RenderContextBase(camera), model);

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();

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
