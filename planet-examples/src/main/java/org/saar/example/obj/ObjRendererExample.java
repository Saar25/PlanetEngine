package org.saar.example.obj;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.components.KeyboardMovementComponent;
import org.saar.core.common.components.KeyboardRotationComponent;
import org.saar.core.common.obj.Obj;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjRenderer;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.renderer.RenderContext;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbo.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.BasicAttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex;
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget;
import org.saar.lwjgl.opengl.fbo.rendertarget.RenderTarget;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;

public class ObjRendererExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static ColourAttachment colorAttachment;
    private static DepthAttachment depthAttachment;
    private static Fbo fbo;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        colorAttachment = new ColourAttachment(0,
                new RenderBufferAttachmentBuffer(InternalFormat.RGBA8),
                new SimpleAllocationStrategy());

        depthAttachment = new DepthAttachment(
                new RenderBufferAttachmentBuffer(InternalFormat.DEPTH24),
                new SimpleAllocationStrategy());

        final Keyboard keyboard = window.getKeyboard();

        final Camera camera = buildCamera(keyboard);

        final ObjModel cottageModel = loadCottage();

        final ObjRenderer renderer = ObjRenderer.INSTANCE;

        fbo = createFbo(WIDTH, HEIGHT);

        window.addResizeListener(e -> {
            fbo.delete();
            fbo = createFbo(window.getWidth(), window.getHeight());
        });

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            fbo.bind();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            camera.update();

            renderer.render(new RenderContext(camera), cottageModel);

            fbo.blitToScreen();

            window.swapBuffers();
            window.pollEvents();

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        camera.delete();
        renderer.delete();
        fbo.delete();
        colorAttachment.delete();
        window.destroy();
    }

    private static Camera buildCamera(Keyboard keyboard) {
        final Projection projection = new ScreenPerspectiveProjection(70f, 1, 1000);

        final NodeComponentGroup components = new NodeComponentGroup(
                new KeyboardMovementComponent(keyboard, 20f, 20f, 20f),
                new KeyboardRotationComponent(keyboard, 50f));

        final Camera camera = new Camera(projection, components);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));
        return camera;
    }

    private static ObjModel loadCottage() {
        try {
            final ObjMesh mesh = Obj.mesh("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Fbo createFbo(int width, int height) {
        final Fbo fbo = Fbo.create(width, height);

        final AttachmentIndex colourIndex = new ColourAttachmentIndex(0);
        final AttachmentIndex depthIndex = new BasicAttachmentIndex(AttachmentType.DEPTH);
        final RenderTarget target = new IndexRenderTarget(colourIndex);

        fbo.addAttachment(colourIndex, colorAttachment);
        fbo.setDrawTarget(target);
        fbo.setReadTarget(target);
        fbo.addAttachment(depthIndex, depthAttachment);

        fbo.ensureStatus();

        return fbo;
    }

}
