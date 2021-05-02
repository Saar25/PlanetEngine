package org.saar.example.shadow;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.obj.ObjNodeBatch;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.renderer.deferred.DeferredRenderNode;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.RenderPassesPipeline;
import org.saar.core.renderer.deferred.shadow.*;
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
import org.saar.maths.Angle;
import org.saar.maths.transform.Position;

import java.util.Objects;

public class ShadowExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    private static float scrollSpeed = 50f;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        final Camera camera = buildCamera();

        final NodeBatch3D nodeBatch3D = buildNodeBatch3D();

        final ObjNodeBatch objNodeBatch = buildObjNodeBatch();

        final ShadowsRenderNode shadowsRenderNode =
                new ShadowsRenderNodeGroup(nodeBatch3D, objNodeBatch);

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);

        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.VERY_HIGH, shadowProjection, light, shadowsRenderNode);
        final ReadOnlyTexture shadowMap = shadowsRenderingPath.render().toTexture();

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final RenderPassesPipeline renderPassesPipeline = new RenderPassesPipeline(
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, light)
        );

        final DeferredRenderNode renderNode = new DeferredRenderNodeGroup(nodeBatch3D, objNodeBatch);
        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(
                screenPrototype, camera, renderNode, renderPassesPipeline);

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);
        mouse.addScrollListener(e -> {
            scrollSpeed += e.getOffset();
            scrollSpeed = Math.max(scrollSpeed, 1);
        });

        final Fps fps = new Fps();
        final Keyboard keyboard = window.getKeyboard();
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

        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static Camera buildCamera() {
        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));
        return camera;
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

    private static ObjNodeBatch buildObjNodeBatch() {
        final ObjModel cottageModel = Objects.requireNonNull(loadCottage());
        final ObjNode cottage = new ObjNode(cottageModel);

        final ObjModel dragonModel = Objects.requireNonNull(loadDragon());
        dragonModel.getTransform().getPosition().set(50, 0, 0);
        final ObjNode dragon = new ObjNode(dragonModel);

        final ObjModel stallModel = Objects.requireNonNull(loadStall());
        stallModel.getTransform().getPosition().set(-50, 0, 0);
        stallModel.getTransform().getRotation().rotate(Angle.degrees(0), Angle.degrees(180), Angle.degrees(0));
        final ObjNode stall = new ObjNode(stallModel);

        return new ObjNodeBatch(cottage, dragon, stall);
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
