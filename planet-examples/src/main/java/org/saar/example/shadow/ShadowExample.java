package org.saar.example.shadow;

import org.saar.core.camera.Camera;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjRenderNode;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.shadow.ShadowsQuality;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderingPath;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.maths.transform.Position;

public class ShadowExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, false);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        MyNode node;
        ObjRenderNode renderNode = null;
        Texture2D texture;
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            node = new MyNode(texture);

            renderNode = new ObjRenderNode(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(renderNode);

        final Node3D cube = new Spatial3D();
        cube.getTransform().getScale().set(10, 10, 10);
        cube.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Node3D[]{cube});
        final RenderNode3D cubeRenderNode = new RenderNode3D(cubeMesh);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(cubeRenderNode);

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final Keyboard keyboard = window.getKeyboard();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);

        final OrthographicProjection shadowProjection = new OrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.HIGH, shadowProjection, light);
        shadowsRenderingPath.addRenderer(renderer3D);
        shadowsRenderingPath.addRenderer(renderer);

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(camera, screenPrototype);
        deferredRenderer.addRenderer(renderer);
        deferredRenderer.addRenderer(renderer3D);

        deferredRenderer.addRenderPass(new ShadowsRenderPass(camera,
                shadowsRenderingPath.getCamera(), shadowsRenderingPath.getShadowMap()));
        shadowsRenderingPath.render();

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            deferredRenderer.render();

            window.update(true);
            window.pollEvents();

            final long delta = System.nanoTime() - current;
            ExamplesUtils.move(camera, keyboard, delta / 1000000);

            final float fps = 1_000_000_000f / (delta);
            System.out.print("\r" +
                    "Fps: " + String.format("%.2f", fps) +
                    ", Delta: " + delta +
                    ", Current: " + current);
            current = System.nanoTime();
        }

        renderer.delete();
        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

}
