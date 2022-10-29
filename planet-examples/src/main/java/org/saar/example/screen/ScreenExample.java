package org.saar.example.screen;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.components.KeyboardMovementComponent;
import org.saar.core.common.components.KeyboardRotationComponent;
import org.saar.core.common.obj.Obj;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjRenderer;
import org.saar.core.mesh.Mesh;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.renderer.RenderContext;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.IFbo;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.MultisampledAllocationStrategy;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;

public class ScreenExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final Keyboard keyboard = window.getKeyboard();

        final Camera camera = buildCamera(keyboard);

        final ObjModel cottageModel = loadCottage();

        final ObjRenderer renderer = ObjRenderer.INSTANCE;

        final IFbo fbo = Fbo.create(WIDTH, HEIGHT);
        final MyScreenPrototype screenPrototype = new MyScreenPrototype();
        final AllocationStrategy allocation = new MultisampledAllocationStrategy(4);
        final OffScreen screen = Screens.fromPrototype(screenPrototype, fbo, allocation);

        window.addResizeListener(e -> {
            final int w = e.getWidth().getAfter();
            final int h = e.getHeight().getAfter();
            screen.resize(w, h);
        });

        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            screen.setAsDraw();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            camera.update();

            renderer.render(new RenderContext(camera), cottageModel);

            screen.copyTo(MainScreen.INSTANCE);

            window.swapBuffers();
            window.pollEvents();
        }

        camera.delete();
        renderer.delete();
        screen.delete();
        window.destroy();
    }

    private static Camera buildCamera(Keyboard keyboard) {
        final Projection projection = new ScreenPerspectiveProjection(70f, 1, 1000);

        final NodeComponentGroup component = new NodeComponentGroup(
                new KeyboardMovementComponent(keyboard, 50f, 50f, 50f),
                new KeyboardRotationComponent(keyboard, 50f));

        final Camera camera = new Camera(projection, component);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));
        return camera;
    }

    private static ObjModel loadCottage() {
        try {
            final Mesh mesh = Obj.mesh("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
