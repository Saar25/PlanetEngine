package org.saar.example.reflected;

import org.joml.Planef;
import org.saar.core.camera.Camera;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.flatreflected.FlatReflectedDeferredRenderer;
import org.saar.core.common.flatreflected.FlatReflectedMesh;
import org.saar.core.common.flatreflected.FlatReflectedRenderNode;
import org.saar.core.common.flatreflected.FlatReflectedVertex;
import org.saar.core.common.obj.*;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.light.LightRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsQuality;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderingPath;
import org.saar.core.renderer.reflection.Reflection;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.ColourTexture;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.maths.utils.Vector3;

import java.util.Objects;

public class ReflectionExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static float scrollSpeed = 50f;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final Camera camera = new Camera(projection);

        final ObjRenderNode cottageNode = Objects.requireNonNull(loadCottage());

        final ObjRenderNode dragonNode = Objects.requireNonNull(loadDragon());
        dragonNode.getTransform().getPosition().set(50, 0, 0);

        final ObjRenderNode stallNode = Objects.requireNonNull(loadStall());
        stallNode.getTransform().getPosition().set(-50, 0, 0);
        stallNode.getTransform().getRotation().rotateDegrees(0, 180, 0);

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(cottageNode, dragonNode, stallNode);

        final Node3D cube = new Spatial3D();
        cube.getTransform().getScale().set(10, 10, 10);
        cube.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Node3D[]{cube});
        final RenderNode3D cubeRenderNode = new RenderNode3D(cubeMesh);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(cubeRenderNode);

        final Camera reflectionCamera = new Camera(projection);
        final MyScreenPrototype reflectionScreenPrototype = new MyScreenPrototype();
        final DeferredRenderingPath reflectionDeferredRenderer = new DeferredRenderingPath(camera, reflectionScreenPrototype);
        reflectionDeferredRenderer.addRenderer(renderer);
        reflectionDeferredRenderer.addRenderer(renderer3D);
        reflectionDeferredRenderer.addRenderPass(new LightRenderPass(camera));

        final Reflection reflection = new Reflection(new Planef(Vector3.of(0, 20, 30), Vector3.forward()), camera,
                reflectionCamera, reflectionDeferredRenderer);

        final FlatReflectedRenderNode mirror = new FlatReflectedRenderNode(FlatReflectedMesh.load(
                new FlatReflectedVertex[]{
                        FlatReflectedVertex.of(Vector3.of(-0.5f, -0.5f, +0.5f), Vector3.forward()), // 0
                        FlatReflectedVertex.of(Vector3.of(-0.5f, +0.5f, +0.5f), Vector3.forward()), // 1
                        FlatReflectedVertex.of(Vector3.of(+0.5f, +0.5f, +0.5f), Vector3.forward()), // 2
                        FlatReflectedVertex.of(Vector3.of(+0.5f, -0.5f, +0.5f), Vector3.forward()), // 3
                }, new int[]{3, 2, 1, 3, 1, 0}
        ));
        mirror.getTransform().getPosition().set(0, 20, 30);
        mirror.getTransform().getScale().scale(10);
        final FlatReflectedDeferredRenderer flatReflectedDeferredRenderer = new FlatReflectedDeferredRenderer(
                new FlatReflectedRenderNode[]{mirror}, reflectionScreenPrototype.getColourTexture());

        final Keyboard keyboard = window.getKeyboard();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);

        final OrthographicProjection shadowProjection = new OrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.VERY_HIGH, shadowProjection, light);
        shadowsRenderingPath.addRenderer(renderer3D);
        shadowsRenderingPath.addRenderer(renderer);

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();
        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(camera, screenPrototype);
        deferredRenderer.addRenderer(flatReflectedDeferredRenderer);
        deferredRenderer.addRenderer(renderer3D);
        deferredRenderer.addRenderer(renderer);

        deferredRenderer.addRenderPass(new ShadowsRenderPass(camera,
                shadowsRenderingPath.getCamera(), shadowsRenderingPath.getShadowMap(), light));
        shadowsRenderingPath.render();

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);
        mouse.addScrollListener(e -> {
            scrollSpeed += e.getOffset();
            scrollSpeed = Math.max(scrollSpeed, 1);
        });

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            reflection.updateReflectionMap();

            deferredRenderer.render();

            window.update(true);
            window.pollEvents();

            final long delta = System.currentTimeMillis() - current;
            ExamplesUtils.move(camera, keyboard, delta, scrollSpeed);

            final float fps = 1000f / delta;
            System.out.print("\r --> " +
                    "Speed: " + String.format("%.2f", scrollSpeed) +
                    ", Fps: " + String.format("%.2f", fps) +
                    ", Delta: " + delta);
            current = System.currentTimeMillis();
        }

        renderer.delete();
        reflection.delete();
        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static ObjRenderNode loadCottage() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            final ObjNode node = new ObjSpatial(texture);
            return new ObjRenderNode(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjRenderNode loadStall() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/stall/stall.model.obj");
            final Texture2D texture = Texture2D.of("/assets/stall/stall.diffuse.png");
            final ObjNode node = new ObjSpatial(texture);
            return new ObjRenderNode(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjRenderNode loadDragon() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/dragon/dragon.model.obj");
            final ReadOnlyTexture texture = ColourTexture.of(255, 215, 0, 255);
            final ObjNode node = new ObjSpatial(texture);
            return new ObjRenderNode(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
