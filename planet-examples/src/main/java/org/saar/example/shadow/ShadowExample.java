package org.saar.example.shadow;

import org.saar.core.behavior.BehaviorGroup;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.behaviors.KeyboardMovementBehavior;
import org.saar.core.common.behaviors.KeyboardMovementScrollVelocityBehavior;
import org.saar.core.common.behaviors.MouseRotationBehavior;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.obj.ObjNodeBatch;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.renderer.deferred.DeferredRenderNode;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderPassesPipeline;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass;
import org.saar.core.renderer.shadow.ShadowsQuality;
import org.saar.core.renderer.shadow.ShadowsRenderNode;
import org.saar.core.renderer.shadow.ShadowsRenderNodeGroup;
import org.saar.core.renderer.shadow.ShadowsRenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.texture.ColourTexture;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.maths.Angle;
import org.saar.maths.transform.Position;

import java.util.Objects;

public class ShadowExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        final Keyboard keyboard = window.getKeyboard();
        final Mouse mouse = window.getMouse();

        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.INSTANCE, 70f, 1, 1000);

        final KeyboardMovementBehavior cameraMovementBehavior =
                new KeyboardMovementBehavior(keyboard, 50f, 50f, 50f);
        final BehaviorGroup behaviors = new BehaviorGroup(cameraMovementBehavior,
                new KeyboardMovementScrollVelocityBehavior(mouse),
                new MouseRotationBehavior(mouse, -.3f));

        final Camera camera = new Camera(projection, behaviors);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

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
        final ReadOnlyTexture2D shadowMap = shadowsRenderingPath.render().getBuffers().getDepth();

        final DeferredRenderPassesPipeline renderPassesPipeline = new DeferredRenderPassesPipeline(
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, light)
        );

        final DeferredRenderNode renderNode = new DeferredRenderNodeGroup(nodeBatch3D, objNodeBatch);
        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(
                camera, renderNode, renderPassesPipeline);

        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            camera.update();

            deferredRenderer.render().toMainScreen();

            window.swapBuffers();
            window.pollEvents();

            final double delta = fps.delta() * 1000;

            System.out.print("\r --> " +
                    "Speed: " + String.format("%.2f", cameraMovementBehavior.getVelocity().x()) +
                    ", Fps: " + String.format("%.2f", fps.fps()) +
                    ", Delta: " + delta);
            fps.update();
        }

        camera.delete();
        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
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
