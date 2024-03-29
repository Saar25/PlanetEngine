package org.saar.example.deferred;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.components.SmoothMouseRotationComponent;
import org.saar.core.common.components.ThirdPersonViewComponent;
import org.saar.core.common.obj.Obj;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.obj.ObjNodeBatch;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.mesh.Mesh;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderingPipeline;
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass;
import org.saar.core.renderer.deferred.passes.LightRenderPass;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.maths.transform.Position;
import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.transform.Transform;

import java.util.Objects;

public class DeferredExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        ClearColour.set(.1f, .1f, .1f);

        final Keyboard keyboard = window.getKeyboard();
        final Mouse mouse = window.getMouse();

        final Camera camera = buildCamera(mouse);

        final DeferredRenderNodeGroup renderNode = buildRenderNode();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-50f, -50f, -50f);
        light.getColour().set(1.0f, 1.0f, 1.0f);

        final DeferredRenderingPipeline renderPassesPipeline = new DeferredRenderingPipeline(
                new DeferredGeometryPass(renderNode),
                new LightRenderPass(light)
        );

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(camera, renderPassesPipeline);

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            camera.update();

            deferredRenderer.render().toMainScreen();

            window.swapBuffers();
            window.pollEvents();

            System.out.print("\rFps: " + 1000f / (-current + (current = System.currentTimeMillis())));
        }

        camera.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static Camera buildCamera(Mouse mouse) {
        final Projection projection = new ScreenPerspectiveProjection(70f, 1, 1000);

        final Transform center = new SimpleTransform();

        final NodeComponentGroup components = new NodeComponentGroup(
                new SmoothMouseRotationComponent(mouse, -.3f),
                new ThirdPersonViewComponent(center, 80));

        final Camera camera = new Camera(projection, components);

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
        final Mesh cubeMesh = R3D.mesh(new Instance3D[]{cubeInstance},
                ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices);
        final Model3D cubeModel = new Model3D(cubeMesh);
        final Node3D cube = new Node3D(cubeModel);

        return new NodeBatch3D(cube);
    }

    private static ObjModel loadCottage() {
        try {
            final Mesh mesh = Obj.mesh("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
