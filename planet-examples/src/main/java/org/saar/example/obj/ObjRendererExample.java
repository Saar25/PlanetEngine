package org.saar.example.obj;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.renderer.obj.ObjRenderNode;
import org.saar.core.renderer.obj.ObjRenderer;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.colour.ColourAttachmentMS;
import org.saar.lwjgl.opengl.fbos.attachment.depth.DepthAttachmentMS;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.Angle;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

public class ObjRendererExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static ColourAttachmentMS colorAttachment;
    private static DepthAttachmentMS depthAttachment;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        colorAttachment = new ColourAttachmentMS(0, AttachmentRenderBuffer.create(FormatType.BGRA), 16);
        depthAttachment = new DepthAttachmentMS(AttachmentRenderBuffer.create(DepthFormatType.COMPONENT24), 16);

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 0, 200));
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        MyNode node;
        ObjRenderNode renderNode = null;
        try {
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            node = new MyNode(texture);

            renderNode = ObjRenderNode.load("/assets/cottage/cottage.obj", node);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        final ObjRenderer renderer = new ObjRenderer(camera, new ObjRenderNode[]{renderNode});

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            fbo.bind();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            move(camera, keyboard);
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
        colorAttachment.delete();
    }

    private static void move(ICamera camera, Keyboard keyboard) {
        final Vector3f toMove = Vector3.zero();
        final Vector3f toRotate = Vector3.zero();
        if (keyboard.isKeyPressed('W')) {
            toMove.add(0, 0, 1);
        }
        if (keyboard.isKeyPressed('A')) {
            toMove.add(1, 0, 0);
        }
        if (keyboard.isKeyPressed('S')) {
            toMove.add(0, 0, -1);
        }
        if (keyboard.isKeyPressed('D')) {
            toMove.add(-1, 0, 0);
        }
        if (keyboard.isKeyPressed('Q')) {
            toRotate.add(0, .5f, 0);
        }
        if (keyboard.isKeyPressed('E')) {
            toRotate.add(0, -.5f, 0);
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            toMove.add(0, -1, 0);
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            toMove.add(0, 1, 0);
        }
        camera.getTransform().getPosition().add(toMove.rotate(camera.getTransform().getRotation().getValue()).mul(-1));
        camera.getTransform().addRotation(Angle.degrees(toRotate.x), Angle.degrees(toRotate.y), Angle.degrees(toRotate.z));
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height);
        fbo.setDrawAttachments(colorAttachment);
        fbo.setReadAttachment(colorAttachment);
        fbo.addAttachment(colorAttachment);
        fbo.addAttachment(depthAttachment);
        fbo.ensureStatus();
        return fbo;
    }

}
