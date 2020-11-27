package org.saar.example.renderer3d;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.r3d.*;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Quaternion;

public class Renderer3DExample {

    private static final boolean optimizeMesh = true;
    private static final boolean singleBatch = true;

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static final int CUBES = 100_000;
    private static final int AREA = 1000;
    private static final int BATCHES = singleBatch ? 1 : 10_000;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, false);
        window.init();

        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, -1000);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final Renderer3D renderer = new Renderer3D(models());

        final Keyboard keyboard = window.getKeyboard();
        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            ExamplesUtils.move(camera, keyboard);
            renderer.render(new RenderContextBase(camera));

            window.pollEvents();
            window.update(true);

            System.out.print("\rFps: " + fps.fps());

            fps.update();
        }

        renderer.delete();
        window.destroy();
    }

    private static Model3D[] models() {
        final int cubesPerBatch = CUBES / BATCHES;
        final Model3D[] batches = new Model3D[BATCHES];
        final Instance3D[] nodes = new Instance3D[cubesPerBatch];
        for (int i = 0; i < BATCHES; i++) {
            for (int j = 0; j < cubesPerBatch; j++) {
                final Instance3D newNode = R3D.instance();
                final float x = (float) (Math.random() * AREA - AREA / 2);
                final float y = (float) (Math.random() * AREA - AREA / 2);
                final float z = (float) (Math.random() * AREA - AREA / 2);
                newNode.getTransform().getPosition().set(x, y, z);
                newNode.getTransform().getRotation().set(Quaternion.of(
                        (float) Math.random(), (float) Math.random(),
                        (float) Math.random(), (float) Math.random()).normalize());
                nodes[j] = newNode;
            }
            final Mesh3DPrototype prototype = optimizeMesh ? new MeshOptimized() : new MeshUnoptimized();
            final Mesh3D mesh = Mesh3D.load(prototype, ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, nodes);
            batches[i] = new Model3D(mesh);
        }
        return batches;
    }

}
