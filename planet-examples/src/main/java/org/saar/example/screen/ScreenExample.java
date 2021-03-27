package org.saar.example.screen;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjRenderer;
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
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

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

        final ObjRenderer renderer = new ObjRenderer(model);

        final IFbo fbo = new MultisampledFbo(WIDTH, HEIGHT, 16);
        final MyScreenPrototype screenPrototype = new MyScreenPrototype();
        final OffScreen screen = Screens.fromPrototype(screenPrototype, fbo);

        window.addResizeListener(e -> {
            final int w = e.getWidth().getAfter();
            final int h = e.getHeight().getAfter();
            screen.resize(w, h);
        });

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            screen.setAsDraw();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            ExamplesUtils.move(camera, keyboard);
            renderer.render(new RenderContextBase(camera));

            screen.copyTo(MainScreen.getInstance());

            window.update(true);
            window.pollEvents();
        }

        renderer.delete();
        screen.delete();
        window.destroy();
    }
}
