package org.saar.example.screen;

import org.saar.core.camera.Camera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.obj.*;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;

public class ScreenExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        ObjNode node;
        ObjModel model = null;
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            node = Obj.node(texture);

            model = new ObjModel(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        final ObjRenderer renderer = new ObjRenderer(model);

        final IFbo fbo = new MultisampledFbo(WIDTH, HEIGHT, 16);
        final MyScreenPrototype screenPrototype = new MyScreenPrototype();
        final OffScreen screen = Screens.fromPrototype(screenPrototype, fbo);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            screen.setAsDraw();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            ExamplesUtils.move(camera, keyboard);
            renderer.render(new RenderContextBase(camera));

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
