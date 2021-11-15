package org.saar.example.renderer3d;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.components.KeyboardMovementComponent;
import org.saar.core.common.components.KeyboardRotationComponent;
import org.saar.core.common.r3d.*;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.renderer.RenderContext;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class ManyCubesExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static final int CUBES = 50_000;
    private static final float SPACE = 2f;
    private static final int ROWS = 10;
    private static final int COLS = 10;


    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        final Keyboard keyboard = window.getKeyboard();

        final Projection projection = new ScreenPerspectiveProjection(70f, 1, 1000);

        final NodeComponentGroup components = new NodeComponentGroup(
                new KeyboardMovementComponent(keyboard, 20f, 20f, 20f),
                new KeyboardRotationComponent(keyboard, 50f));

        final Camera camera = new Camera(projection, components);

        final Model3D model = model();
        final Renderer3D renderer = Renderer3D.INSTANCE;

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            camera.update();

            final int size = (int) Math.ceil(Math.pow(CUBES, 1 / 3.0) * SPACE);

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    model.getTransform().getPosition().set(
                            i * size - size / 2f, 0, j * size - size / 2f);
                    renderer.render(new RenderContext(camera), model);
                }
            }

            window.pollEvents();
            window.swapBuffers();

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        camera.delete();
        renderer.delete();
        window.destroy();
    }

    private static Model3D model() {
        final int size = (int) Math.pow(CUBES, 1 / 3f);
        final Instance3D[] nodes = new Instance3D[CUBES];
        for (int i = 0; i < CUBES; i++) {
            final int a = i / (size * size);
            final int b = (i / size) % size;
            final int c = i % size;
            final Instance3D newNode = R3D.instance();
            newNode.getTransform().getPosition().set(
                    a * SPACE, b * SPACE, c * SPACE);
            nodes[i] = newNode;
        }
        final Mesh3D mesh = R3D.mesh(nodes,
                ExamplesUtils.cubeVertices,
                ExamplesUtils.cubeIndices);
        return new Model3D(mesh);
    }

}
