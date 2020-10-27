package org.saar.example.smooth;

import org.saar.core.camera.Camera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.r3d.*;
import org.saar.core.common.smooth.*;
import org.saar.core.common.terrain.smooth.SmoothTerrain;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.light.LightRenderPass;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

public class SmoothExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final SmoothNode node = Smooth.node();
        final SmoothMesh mesh = SmoothMesh.load(new SmoothVertex[]{
                Smooth.vertex(
                        Vector3.of(-0.5f, -0.5f, -0.5f), Vector3.of(+0, +0, -1), Vector3.of(0.5f, 0.5f, 0.0f),
                        Vector3.of(-0.5f, -0.5f, -0.5f).mul((float) Math.random() * 2 - 1)), // 0
                Smooth.vertex(
                        Vector3.of(-0.5f, +0.5f, -0.5f), Vector3.of(+0, +1, +0), Vector3.of(0.5f, 1.0f, 0.5f),
                        Vector3.of(-0.5f, +0.5f, -0.5f).mul((float) Math.random() * 2 - 1)), // 1
                Smooth.vertex(
                        Vector3.of(+0.5f, +0.5f, -0.5f), Vector3.of(+1, +0, +0), Vector3.of(1.0f, 0.5f, 0.5f),
                        Vector3.of(+0.5f, +0.5f, -0.5f).mul((float) Math.random() * 2 - 1)), // 2
                Smooth.vertex(
                        Vector3.of(+0.5f, -0.5f, -0.5f), Vector3.of(+0, -1, +0), Vector3.of(0.5f, 0.0f, 0.5f),
                        Vector3.of(+0.5f, -0.5f, -0.5f).mul((float) Math.random() * 2 - 1)), // 3
                Smooth.vertex(
                        Vector3.of(-0.5f, -0.5f, +0.5f), Vector3.of(-1, +0, +0), Vector3.of(0.0f, 0.5f, 0.5f),
                        Vector3.of(-0.5f, -0.5f, +0.5f).mul((float) Math.random() * 2 - 1)), // 4
                Smooth.vertex(
                        Vector3.of(-0.5f, +0.5f, +0.5f), Vector3.of(+0, +0, +0), Vector3.of(0.5f, 0.5f, 0.5f),
                        Vector3.of(-0.5f, +0.5f, +0.5f).mul((float) Math.random() * 2 - 1)), // 5
                Smooth.vertex(
                        Vector3.of(+0.5f, +0.5f, +0.5f), Vector3.of(+0, +0, +0), Vector3.of(0.5f, 0.5f, 0.5f),
                        Vector3.of(+0.5f, +0.5f, +0.5f).mul((float) Math.random() * 2 - 1)), // 6
                Smooth.vertex(
                        Vector3.of(+0.5f, -0.5f, +0.5f), Vector3.of(+0, +0, +1), Vector3.of(0.5f, 0.5f, 1.0f),
                        Vector3.of(+0.5f, -0.5f, +0.5f).mul((float) Math.random() * 2 - 1)), // 7
        }, new int[]{
                0, 1, 2, 0, 2, 3, // back   , PV: 0
                4, 5, 1, 4, 1, 0, // left   , PV: 4
                7, 6, 5, 7, 5, 4, // front  , PV: 7
                2, 6, 7, 2, 7, 3, // right  , PV: 2
                1, 5, 6, 1, 6, 2, // top    , PV: 1
                3, 7, 4, 3, 4, 0, // bottom , PV: 3
        });
        final SmoothModel model = new SmoothModel(node, mesh);
        node.getTransform().getScale().set(10, 10, 10);
        node.getTransform().getPosition().set(0, 15, 50);

        final SmoothNode terrainNode = Smooth.node();
        final SmoothMesh terrainMesh = SmoothTerrain.generateMesh();
        final SmoothModel terrainModel = new SmoothModel(terrainNode, terrainMesh);
        terrainNode.getTransform().getScale().scale(50);

        final SmoothDeferredRenderer renderer = new SmoothDeferredRenderer(model, terrainModel);

        final Node3D cube = R3D.node();
        cube.getTransform().getScale().set(10, 10, 10);
        cube.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Node3D[]{cube});
        final Model3D cubeModel = new Model3D(cubeMesh);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(cubeModel);

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(camera, screenPrototype);
        deferredRenderer.addRenderer(renderer3D);
        deferredRenderer.addRenderer(renderer);
        deferredRenderer.addRenderPass(new LightRenderPass(camera));

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);

        long current = System.currentTimeMillis();
        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {

            deferredRenderer.render();

            window.update(true);
            window.pollEvents();

            final long delta = System.currentTimeMillis() - current;
            ExamplesUtils.move(camera, keyboard, delta, 30f);

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );

            if (keyboard.isKeyPressed('I')) {
                renderer.getTargetScalar().set(renderer.getTargetScalar().get() + 0.01f);
            } else if (keyboard.isKeyPressed('K')) {
                renderer.getTargetScalar().set(renderer.getTargetScalar().get() - 0.01f);
            }
        }

        renderer.delete();
        deferredRenderer.delete();
        window.destroy();
    }

}
