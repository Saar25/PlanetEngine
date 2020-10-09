package org.saar.example.normalmapping;

import org.saar.core.camera.Camera;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.normalmap.*;
import org.saar.core.common.obj.*;
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
import org.saar.lwjgl.opengl.textures.ColourTexture;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;

import java.util.Objects;

public class NormalMappingExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static float scrollSpeed = 50f;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final ObjModel cottageNode = Objects.requireNonNull(loadCottage());

        final ObjModel dragonNode = Objects.requireNonNull(loadDragon());
        dragonNode.getTransform().getPosition().set(50, 0, 0);

        final ObjModel stallNode = Objects.requireNonNull(loadStall());
        stallNode.getTransform().getRotation().rotateDegrees(0, 180, 0);
        stallNode.getTransform().getPosition().set(-50, 0, 0);

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(cottageNode, dragonNode, stallNode);

        final NormalMappedModel boulder = Objects.requireNonNull(loadBoulder());
        boulder.getTransform().getPosition().set(0, 20, 0);

        final NormalMappedModel barrel = Objects.requireNonNull(loadBarrel());
        barrel.getTransform().getPosition().set(-20, 20, 0);

        final NormalMappedModel crate = Objects.requireNonNull(loadCrate());
        crate.getTransform().getPosition().set(+20, 20, 0);
        crate.getTransform().getScale().scale(.05f);

        final NormalMappedDeferredRenderer normalMappedRenderer =
                new NormalMappedDeferredRenderer(boulder, barrel, crate);

        final Node3D cube = new Spatial3D();
        cube.getTransform().getScale().set(10, 10, 10);
        cube.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Node3D[]{cube});
        final Model3D cubeModel = new Model3D(cubeMesh);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(cubeModel);

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final Keyboard keyboard = window.getKeyboard();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);

        final OrthographicProjection shadowProjection = new OrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.VERY_HIGH, shadowProjection, light);
        shadowsRenderingPath.addRenderer(normalMappedRenderer);
        shadowsRenderingPath.addRenderer(renderer3D);
        shadowsRenderingPath.addRenderer(renderer);

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(camera, screenPrototype);
        deferredRenderer.addRenderer(normalMappedRenderer);
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
        GlUtils.setClearColour(0, .7f, .9f);

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
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

        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static ObjModel loadCottage() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            final ObjNode node = new ObjSpatial(texture);
            return new ObjModel(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadStall() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/stall/stall.model.obj");
            final Texture2D texture = Texture2D.of("/assets/stall/stall.diffuse.png");
            final ObjNode node = new ObjSpatial(texture);
            return new ObjModel(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadDragon() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/dragon/dragon.model.obj");
            final ReadOnlyTexture texture = ColourTexture.of(255, 215, 0, 255);
            final ObjNode node = new ObjSpatial(texture);
            return new ObjModel(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NormalMappedModel loadBoulder() {
        try {
            final NormalMappedMesh mesh = NormalMappedMesh.load("/assets/boulder/boulder.model.obj");
            final ReadOnlyTexture normalMap = Texture2D.of("/assets/boulder/boulder.normal.png");
            final ReadOnlyTexture texture = Texture2D.of("/assets/boulder/boulder.diffuse.png");
            final NormalMappedNode node = NormalMapped.node(texture, normalMap);
            return new NormalMappedModel(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NormalMappedModel loadBarrel() {
        try {
            final NormalMappedMesh mesh = NormalMappedMesh.load("/assets/barrel/barrel.model.obj");
            final ReadOnlyTexture normalMap = Texture2D.of("/assets/barrel/barrel.normal.png");
            final ReadOnlyTexture texture = Texture2D.of("/assets/barrel/barrel.diffuse.png");
            final NormalMappedNode node = NormalMapped.node(texture, normalMap);
            return new NormalMappedModel(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NormalMappedModel loadCrate() {
        try {
            final NormalMappedMesh mesh = NormalMappedMesh.load("/assets/crate/crate.model.obj");
            final ReadOnlyTexture normalMap = Texture2D.of("/assets/crate/crate.normal.png");
            final ReadOnlyTexture texture = Texture2D.of("/assets/crate/crate.diffuse.png");
            final NormalMappedNode node = NormalMapped.node(texture, normalMap);
            return new NormalMappedModel(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
