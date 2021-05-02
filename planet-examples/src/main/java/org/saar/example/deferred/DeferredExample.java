package org.saar.example.deferred;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.r3d.*;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.RenderPassesPipeline;
import org.saar.core.renderer.deferred.light.LightRenderPass;
import org.saar.core.screen.MainScreen;
import org.saar.example.ExamplesUtils;
import org.saar.example.MyScreenPrototype;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;

public class DeferredExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        GlUtils.setClearColour(.1f, .1f, .1f);

        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        ObjModel model = null;
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            model = new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        final ObjNode cottage = new ObjNode(model);

        final Instance3D cubeInstance = R3D.instance();
        cubeInstance.getTransform().getScale().set(10, 10, 10);
        cubeInstance.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Instance3D[]{cubeInstance});
        final Model3D cubeModel = new Model3D(cubeMesh);
        final Node3D cube = new Node3D(cubeModel);
        final NodeBatch3D nodeBatch3D = new NodeBatch3D(cube);

        final MyScreenPrototype screenPrototype =
                new MyScreenPrototype();

        final RenderPassesPipeline renderPassesPipeline =
                new RenderPassesPipeline(new LightRenderPass());

        final DeferredRenderNodeGroup renderNode =
                new DeferredRenderNodeGroup(cottage, nodeBatch3D);

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(
                screenPrototype, camera, renderNode, renderPassesPipeline);

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);

        long current = System.currentTimeMillis();
        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            deferredRenderer.render().toMainScreen();

            window.update(true);
            window.pollEvents();

            final long delta = System.currentTimeMillis() - current;
            ExamplesUtils.move(camera, keyboard, delta, 50f);

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        deferredRenderer.delete();
        window.destroy();
    }

}
