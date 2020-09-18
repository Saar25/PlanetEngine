package org.saar.example.deferred;

import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjRenderNode;
import org.saar.core.renderer.deferred.DeferredRendererBase;
import org.saar.core.renderer.deferred.light.LightRenderPass;
import org.saar.core.renderer.deferred.light.LightRenderPassInput;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.maths.transform.Position;

public class DeferredExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 0, 200));
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        MyNode node;
        ObjRenderNode renderNode = null;
        Texture2D texture = null;
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            node = new MyNode(texture);

            renderNode = new ObjRenderNode(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(camera, new ObjRenderNode[]{renderNode});

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final DeferredRendererBase deferredRenderer = new DeferredRendererBase(screenPrototype, renderer);
        final LightRenderPass lightRenderPass = new LightRenderPass(camera, new LightRenderPassInput(
                screenPrototype.getNormalTexture(), screenPrototype.getDepthTexture()));
        deferredRenderer.addRenderPass(lightRenderPass);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {

            ExamplesUtils.move(camera, keyboard);

            deferredRenderer.render();

            window.update(true);
            window.pollEvents();
        }

        renderer.delete();
        deferredRenderer.delete();
        window.destroy();
    }

}
