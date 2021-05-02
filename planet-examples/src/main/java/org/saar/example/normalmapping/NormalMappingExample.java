package org.saar.example.normalmapping;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.normalmap.*;
import org.saar.core.common.obj.*;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.RenderPassesPipeline;
import org.saar.core.renderer.deferred.shadow.ShadowsQuality;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderingPath;
import org.saar.core.renderer.deferred.ssao.SsaoRenderPass;
import org.saar.core.screen.MainScreen;
import org.saar.example.ExamplesUtils;
import org.saar.example.MyScreenPrototype;
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

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    private static float scrollSpeed = 50f;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final ObjModel cottageModel = Objects.requireNonNull(loadCottage());
        final ObjNode cottage = new ObjNode(cottageModel);

        final ObjModel dragonModel = Objects.requireNonNull(loadDragon());
        dragonModel.getTransform().getPosition().set(50, 0, 0);
        final ObjNode dragon = new ObjNode(dragonModel);

        final ObjModel stallModel = Objects.requireNonNull(loadStall());
        stallModel.getTransform().getRotation().rotateDegrees(0, 180, 0);
        stallModel.getTransform().getPosition().set(-50, 0, 0);
        final ObjNode stall = new ObjNode(stallModel);

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(cottageModel, dragonModel, stallModel);

        final ObjNodeBatch objNodeBatch = new ObjNodeBatch(cottage, dragon, stall);

        final NormalMappedModel boulderModel = Objects.requireNonNull(loadBoulder());
        boulderModel.getTransform().getPosition().set(0, 20, 0);
        final NormalMappedNode boulder = new NormalMappedNode(boulderModel);

        final NormalMappedModel barrelModel = Objects.requireNonNull(loadBarrel());
        barrelModel.getTransform().getPosition().set(-20, 20, 0);
        final NormalMappedNode barrel = new NormalMappedNode(barrelModel);

        final NormalMappedModel crateModel = Objects.requireNonNull(loadCrate());
        crateModel.getTransform().getPosition().set(+20, 20, 0);
        crateModel.getTransform().getScale().scale(.05f);
        final NormalMappedNode crate = new NormalMappedNode(crateModel);

        final NormalMappedDeferredRenderer normalMappedRenderer =
                new NormalMappedDeferredRenderer(boulderModel, barrelModel, crateModel);

        final NormalMappedNodeBatch normalMappedNodeBatch =
                new NormalMappedNodeBatch(boulder, barrel, crate);

        final Instance3D cubeInstance = R3D.instance();
        cubeInstance.getTransform().getScale().set(10, 10, 10);
        cubeInstance.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Instance3D[]{cubeInstance});
        final Model3D cubeModel = new Model3D(cubeMesh);
        final Node3D cube = new Node3D(cubeModel);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(cubeModel);

        final NodeBatch3D nodeBatch3D = new NodeBatch3D(cube);

        final Keyboard keyboard = window.getKeyboard();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);

        final RenderersGroup shadowsRenderersGroup = new RenderersGroup(
                normalMappedRenderer, renderer, renderer3D);
        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.VERY_HIGH, shadowProjection, light, shadowsRenderersGroup);
        final ReadOnlyTexture shadowMap = shadowsRenderingPath.render().toTexture();

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final RenderPassesPipeline renderPassesPipeline = new RenderPassesPipeline(
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, light),
                new SsaoRenderPass()
        );

        final DeferredRenderNodeGroup renderNode =
                new DeferredRenderNodeGroup(cube, normalMappedNodeBatch, objNodeBatch);

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(
                screenPrototype, camera, renderNode, renderPassesPipeline);

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);
        mouse.addScrollListener(e -> {
            scrollSpeed += e.getOffset();
            scrollSpeed = Math.max(scrollSpeed, 1);
        });
        GlUtils.setClearColour(0, .7f, .9f);

        final PostProcessingPipeline pipeline = new PostProcessingPipeline(
                new ContrastPostProcessor(1.3f),
                new FxaaPostProcessor()
        );

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            final ReadOnlyTexture texture = deferredRenderer.render().toTexture();
            pipeline.process(texture).toMainScreen();

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

        pipeline.delete();
        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static ObjModel loadCottage() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadStall() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/stall/stall.model.obj");
            final Texture2D texture = Texture2D.of("/assets/stall/stall.diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadDragon() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/dragon/dragon.model.obj");
            final ReadOnlyTexture texture = ColourTexture.of(255, 215, 0, 255);
            return new ObjModel(mesh, texture);
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
            return new NormalMappedModel(mesh, texture, normalMap);
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
            return new NormalMappedModel(mesh, texture, normalMap);
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
            return new NormalMappedModel(mesh, texture, normalMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
