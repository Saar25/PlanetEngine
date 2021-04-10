package org.saar.example.reflected;

import org.joml.Planef;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.flatreflected.*;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.light.LightRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsQuality;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderingPath;
import org.saar.core.renderer.reflection.Reflection;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.Screen;
import org.saar.core.screen.Screens;
import org.saar.example.ExamplesUtils;
import org.saar.example.MyScreenPrototype;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.textures.ColourTexture;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlCullFace;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.utils.Vector3;

import java.util.Objects;

public class ReflectionExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static float scrollSpeed = 50f;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        GlUtils.setClearColour(.1f, .1f, .1f);

        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);
        final Camera camera = new Camera(projection);

        final ObjModel cottageNode = Objects.requireNonNull(loadCottage());

        final ObjModel dragonNode = Objects.requireNonNull(loadDragon());
        dragonNode.getTransform().getPosition().set(50, 0, 0);

        final ObjModel stallNode = Objects.requireNonNull(loadStall());
        stallNode.getTransform().getPosition().set(-50, 0, 0);
        stallNode.getTransform().getRotation().rotateDegrees(0, 180, 0);

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(cottageNode, dragonNode, stallNode);

        final Instance3D cube = R3D.instance();
        cube.getTransform().getScale().set(10, 10, 10);
        cube.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Instance3D[]{cube});
        final Model3D cubeModel = new Model3D(cubeMesh);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(cubeModel);

        final Camera reflectionCamera = new Camera(projection);

        final RenderersGroup reflectionRenderersGroup = new RenderersGroup(renderer, renderer3D);

        final MyScreenPrototype reflectionScreenPrototype = new MyScreenPrototype();
        final DeferredRenderingPath reflectionRenderingPath = new DeferredRenderingPath(
                reflectionScreenPrototype.asBuffers(), new LightRenderPass(camera));

        final FlatReflectedVertex[] vertices = {
                FlatReflected.vertex(Vector3.of(-0.5f, +0.5f, -0.5f)), // 0
                FlatReflected.vertex(Vector3.of(-0.5f, +0.5f, +0.5f)), // 1
                FlatReflected.vertex(Vector3.of(+0.5f, +0.5f, +0.5f)), // 2
                FlatReflected.vertex(Vector3.of(+0.5f, +0.5f, -0.5f)), // 3
        };
        final FlatReflectedMesh mesh = FlatReflectedMesh.load(vertices, new int[]{3, 2, 1, 3, 1, 0});
        final FlatReflectedModel mirror = new FlatReflectedModel(mesh, Vector3.upward());

        mirror.getTransform().getPosition().set(0, .1f, 30);
        mirror.getTransform().getScale().scale(10);

        final Planef mirrorPlane = new Planef(mirror.getTransform().getPosition().getValue(), mirror.getNormal());
        final Reflection reflection = new Reflection(mirrorPlane, camera, reflectionCamera, reflectionRenderingPath);

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();
        final FlatReflectedDeferredRenderer flatReflectedDeferredRenderer = new FlatReflectedDeferredRenderer(
                new FlatReflectedModel[]{mirror}, reflectionScreenPrototype.getColourTexture());

        final Keyboard keyboard = window.getKeyboard();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);


        final RenderersGroup shadowsRenderersGroup = new RenderersGroup(renderer, renderer3D);
        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.VERY_HIGH, shadowProjection, light);

        final RenderersGroup renderersGroup = new RenderersGroup(flatReflectedDeferredRenderer, renderer3D, renderer);

        final Screen screen = Screens.fromPrototype(screenPrototype, Fbo.create(WIDTH, HEIGHT));

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(screenPrototype.asBuffers(),
                new ShadowsRenderPass(camera, shadowsRenderingPath.getCamera(), shadowsRenderingPath.getShadowMap(), light));

        shadowsRenderingPath.bind();
        final RenderContextBase context = new RenderContextBase(
                shadowsRenderingPath.getCamera());
        context.getHints().cullFace = GlCullFace.FRONT;
        shadowsRenderersGroup.render(context);
        shadowsRenderingPath.render();

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);
        mouse.addScrollListener(e -> {
            scrollSpeed += e.getOffset();
            scrollSpeed = Math.max(scrollSpeed, 1);
        });

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            reflectionRenderersGroup.render(new RenderContextBase(camera));
            reflection.updateReflectionMap();

            screen.setAsDraw();
            GlUtils.clearColourAndDepthBuffer();
            renderersGroup.render(new RenderContextBase(camera));

            deferredRenderer.render().toMainScreen();

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

        reflection.delete();
        renderersGroup.delete();
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

}
