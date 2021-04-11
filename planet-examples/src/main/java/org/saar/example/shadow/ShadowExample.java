package org.saar.example.shadow;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.RenderPassesPipeline;
import org.saar.core.renderer.deferred.shadow.ShadowsQuality;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.example.MyScreenPrototype;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.ColourTexture;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlCullFace;
import org.saar.maths.Angle;
import org.saar.maths.transform.Position;

import java.util.Objects;

public class ShadowExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    private static float scrollSpeed = 50f;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final ObjModel cottageNode = Objects.requireNonNull(loadCottage());

        final ObjModel dragonNode = Objects.requireNonNull(loadDragon());
        dragonNode.getTransform().getPosition().set(50, 0, 0);

        final ObjModel stallNode = Objects.requireNonNull(loadStall());
        stallNode.getTransform().getPosition().set(-50, 0, 0);
        stallNode.getTransform().getRotation().rotate(Angle.degrees(0), Angle.degrees(180), Angle.degrees(0));

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(cottageNode, dragonNode, stallNode);

        final Instance3D cube = R3D.instance();
        cube.getTransform().getScale().set(10, 10, 10);
        cube.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Instance3D[]{cube});
        final Model3D cubeModel = new Model3D(cubeMesh);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(cubeModel);

        final Keyboard keyboard = window.getKeyboard();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);

        final RenderersGroup shadowsRenderersGroup = new RenderersGroup(renderer, renderer3D);
        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.VERY_HIGH, shadowProjection, light);

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final RenderersGroup renderersGroup = new RenderersGroup(renderer, renderer3D);

        final RenderPassesPipeline renderPassesPipeline = new RenderPassesPipeline(
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowsRenderingPath.getShadowMap(), light)
        );

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(
                screenPrototype, camera, renderersGroup, renderPassesPipeline);

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

        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            deferredRenderer.render().toMainScreen();

            window.update(true);
            window.pollEvents();

            final double delta = fps.delta() * 1000;
            ExamplesUtils.move(camera, keyboard, (long) delta, scrollSpeed);

            System.out.print("\r --> " +
                    "Speed: " + String.format("%.2f", scrollSpeed) +
                    ", Fps: " + String.format("%.2f", fps.fps()) +
                    ", Delta: " + delta);
            fps.update();
        }

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
