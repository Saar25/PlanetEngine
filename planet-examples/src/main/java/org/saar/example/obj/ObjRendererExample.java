package org.saar.example.obj;

import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.renderer.obj.ObjRenderNode;
import org.saar.core.renderer.obj.ObjRenderer;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;

public class ObjRendererExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static RenderBufferAttachmentMS attachment;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        attachment = RenderBufferAttachmentMS.ofColour(0, FormatType.BGRA, 16);

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 5000);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 0, -1000));
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        MyNode node;
        ObjRenderNode renderNode = null;
        try {
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            node = new MyNode(texture);

            renderNode = ObjRenderNode.load("/assets/cottage/cottage_obj.obj", node);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        final ObjRenderer renderer = new ObjRenderer(camera, new ObjRenderNode[]{renderNode});

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            fbo.bind();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            renderer.render();

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                fbo.delete();
                fbo = createFbo(window.getWidth(), window.getHeight());
            }
        }

        renderer.delete();
        fbo.delete();
        attachment.delete();
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height);
        fbo.setDrawAttachments(attachment);
        fbo.setReadAttachment(attachment);
        fbo.addAttachment(attachment);
        fbo.ensureStatus();
        return fbo;
    }

}
