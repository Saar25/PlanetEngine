package org.saar.example.smooth;

import org.saar.core.behavior.BehaviorGroup;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.behaviors.KeyboardMovementBehavior;
import org.saar.core.common.r3d.*;
import org.saar.core.common.smooth.*;
import org.saar.core.common.terrain.smooth.SmoothTerrain;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderNode;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderPassesPipeline;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.renderpass.light.LightRenderPass;
import org.saar.core.screen.MainScreen;
import org.saar.example.ExamplesUtils;
import org.saar.example.MyScreenPrototype;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

public class SmoothExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final Keyboard keyboard = window.getKeyboard();

        final Camera camera = buildCamera(keyboard);

        final SmoothNode node = buildSmoothNode();
        final SmoothNode terrain = buildSmoothTerrain();
        final SmoothNodeBatch smoothNodeBatch = new SmoothNodeBatch(node, terrain);

        final NodeBatch3D nodeBatch3D = buildNodeBatch3D();

        final DeferredRenderNode renderNode = new DeferredRenderNodeGroup(nodeBatch3D, smoothNodeBatch);

        final RenderingPath renderingPath = buildRenderingPath(camera, renderNode);

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            camera.update();

            renderingPath.render().toMainScreen();

            window.update(true);
            window.pollEvents();

            System.out.print("\rFps: " + 1000f / (-current + (current = System.currentTimeMillis())));

            final float old = node.getModel().getTarget();
            if (keyboard.isKeyPressed('I')) {
                node.getModel().setTarget(old + 0.01f);
                terrain.getModel().setTarget(old + 0.01f);
            } else if (keyboard.isKeyPressed('K')) {
                node.getModel().setTarget(old - 0.01f);
                terrain.getModel().setTarget(old - 0.01f);
            }
        }

        camera.delete();
        renderingPath.delete();
        window.destroy();
    }

    private static RenderingPath buildRenderingPath(Camera camera, DeferredRenderNode renderNode) {
        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final DeferredRenderPassesPipeline renderPassesPipeline = new DeferredRenderPassesPipeline(
                new LightRenderPass());

        return new DeferredRenderingPath(screenPrototype,
                camera, renderNode, renderPassesPipeline);
    }

    private static SmoothNode buildSmoothTerrain() {
        final SmoothMesh terrainMesh = SmoothTerrain.generateMeshAsync();
        final SmoothModel terrainModel = new SmoothModel(terrainMesh);
        terrainModel.getTransform().getScale().scale(50);
        return new SmoothNode(terrainModel);
    }

    private static SmoothNode buildSmoothNode() {
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
        final SmoothModel model = new SmoothModel(mesh);
        model.getTransform().getScale().set(10, 10, 10);
        model.getTransform().getPosition().set(0, 15, 50);
        return new SmoothNode(model);
    }

    private static NodeBatch3D buildNodeBatch3D() {
        final Instance3D cubeInstance = R3D.instance();
        cubeInstance.getTransform().getScale().set(10, 10, 10);
        cubeInstance.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices,
                ExamplesUtils.cubeIndices, new Instance3D[]{cubeInstance});
        final Model3D cubeModel = new Model3D(cubeMesh);
        final Node3D cube = new Node3D(cubeModel);

        return new NodeBatch3D(cube);
    }

    private static Camera buildCamera(Keyboard keyboard) {
        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);

        final BehaviorGroup behaviors = new BehaviorGroup(
                new KeyboardMovementBehavior(keyboard, 50f, 50f, 50f));

        final Camera camera = new Camera(projection, behaviors);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));
        return camera;
    }

}
