package org.saar.example.deferred;

import org.saar.core.behavior.BehaviorGroup;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.behaviors.FollowTransformBehavior;
import org.saar.core.common.behaviors.MouseRotationBehavior;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.obj.ObjNodeBatch;
import org.saar.core.common.r3d.*;
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
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;
import org.saar.maths.transform.SimpleTransform;

import java.util.Objects;

public class DeferredExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        GlUtils.setClearColour(.1f, .1f, .1f);

        final Keyboard keyboard = window.getKeyboard();
        final Mouse mouse = window.getMouse();

        final Camera camera = buildCamera(mouse);

        final DeferredRenderNodeGroup renderNode = buildRenderNode();

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final DeferredRenderPassesPipeline renderPassesPipeline =
                new DeferredRenderPassesPipeline(new LightRenderPass());

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(
                screenPrototype, camera, renderNode, renderPassesPipeline);

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            camera.update();

            deferredRenderer.render().toMainScreen();

            window.update(true);
            window.pollEvents();

            System.out.print("\rFps: " + 1000f / (-current + (current = System.currentTimeMillis())));
        }

        camera.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static Camera buildCamera(Mouse mouse) {
        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);

        final BehaviorGroup behaviors = new BehaviorGroup(
                new MouseRotationBehavior(mouse, -.3f),
                new FollowTransformBehavior(new SimpleTransform(), 80));

        final Camera camera = new Camera(projection, behaviors);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));
        return camera;
    }

    private static DeferredRenderNodeGroup buildRenderNode() {
        final NodeBatch3D nodeBatch3D = buildNodeBatch3D();

        final ObjNodeBatch objNodeBatch = buildObjNodeBatch();

        return new DeferredRenderNodeGroup(nodeBatch3D, objNodeBatch);
    }

    private static ObjNodeBatch buildObjNodeBatch() {
        final ObjModel cottageModel = Objects.requireNonNull(loadCottage());
        final ObjNode cottage = new ObjNode(cottageModel);

        return new ObjNodeBatch(cottage);
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
}
