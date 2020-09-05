package org.saar.example.deferred;

import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjRenderNode;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;

public class DeferredExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 0, 200));
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        MyNode node;
        ObjRenderNode renderNode = null;
        try {
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            node = new MyNode(texture);

            renderNode = ObjRenderNode.load("/assets/cottage/cottage.obj", node);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(camera, new ObjRenderNode[]{renderNode});

        final IFbo fbo = new MultisampledFbo(WIDTH, HEIGHT, 1);
        final MyScreenPrototype screenPrototype = new MyScreenPrototype();
        final OffScreen screen = Screens.fromPrototype(screenPrototype, fbo);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            screen.setAsDraw();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            ExamplesUtils.move(camera, keyboard);
            renderer.render();

            screen.copyTo(MainScreen.getInstance());

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                final int w = window.getWidth();
                final int h = window.getHeight();
                screen.resize(w, h);
            }
        }

        renderer.delete();
        screen.delete();
        window.destroy();
    }

}
