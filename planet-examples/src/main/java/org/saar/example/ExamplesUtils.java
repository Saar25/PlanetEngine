package org.saar.example;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.Camera;
import org.saar.core.common.r3d.R3D;
import org.saar.core.common.r3d.Vertex3D;
import org.saar.lwjgl.glfw.event.EventListener;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.input.mouse.MouseButton;
import org.saar.lwjgl.glfw.input.mouse.MoveEvent;
import org.saar.maths.Angle;
import org.saar.maths.utils.Vector3;

public final class ExamplesUtils {

    public static final Vertex3D[] cubeVertices = new Vertex3D[]{ // xyz position, xyz normal,
            R3D.vertex(Vector3.of(-0.5f, -0.5f, -0.5f), Vector3.of(+0, +0, -1), Vector3.of(0.5f, 0.5f, 0.0f)), // 0
            R3D.vertex(Vector3.of(-0.5f, +0.5f, -0.5f), Vector3.of(+0, +1, +0), Vector3.of(0.5f, 1.0f, 0.5f)), // 1
            R3D.vertex(Vector3.of(+0.5f, +0.5f, -0.5f), Vector3.of(+1, +0, +0), Vector3.of(1.0f, 0.5f, 0.5f)), // 2
            R3D.vertex(Vector3.of(+0.5f, -0.5f, -0.5f), Vector3.of(+0, -1, +0), Vector3.of(0.5f, 0.0f, 0.5f)), // 3
            R3D.vertex(Vector3.of(-0.5f, -0.5f, +0.5f), Vector3.of(-1, +0, +0), Vector3.of(0.0f, 0.5f, 0.5f)), // 4
            R3D.vertex(Vector3.of(-0.5f, +0.5f, +0.5f), Vector3.of(+0, +0, +0), Vector3.of(0.5f, 0.5f, 0.5f)), // 5
            R3D.vertex(Vector3.of(+0.5f, +0.5f, +0.5f), Vector3.of(+0, +0, +0), Vector3.of(0.5f, 0.5f, 0.5f)), // 6
            R3D.vertex(Vector3.of(+0.5f, -0.5f, +0.5f), Vector3.of(+0, +0, +1), Vector3.of(0.5f, 0.5f, 1.0f)), // 7
    };

    public static final int[] cubeIndices = {
            0, 1, 2, 0, 2, 3, // back   , PV: 0
            4, 5, 1, 4, 1, 0, // left   , PV: 4
            7, 6, 5, 7, 5, 4, // front  , PV: 7
            2, 6, 7, 2, 7, 3, // right  , PV: 2
            1, 5, 6, 1, 6, 2, // top    , PV: 1
            3, 7, 4, 3, 4, 0, // bottom , PV: 3
    };

    private ExamplesUtils() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static void move(Camera camera, Keyboard keyboard) {
        ExamplesUtils.move(camera, keyboard, 100);
    }

    public static void move(Camera camera, Keyboard keyboard, long ms) {
        ExamplesUtils.move(camera, keyboard, 100, 100f);
    }

    public static void move(Camera camera, Keyboard keyboard, long ms, float speed) {
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
            toMove.add(0, 1, 0);
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            toMove.add(0, -1, 0);
        }
        toMove.mul(ms / 1000f).mul(speed);
        toRotate.mul(ms / 1000f).mul(100);
        camera.getTransform().getPosition().add(toMove.rotate(camera.getTransform().getRotation().getValue()).mul(-1));
        camera.getTransform().getRotation().rotate(
                Angle.degrees(toRotate.x),
                Angle.degrees(toRotate.y),
                Angle.degrees(toRotate.z));
    }

    public static void addRotationListener(Camera camera, Mouse mouse) {
        mouse.addMoveListener(new EventListener<MoveEvent>() {
            private int lastX = mouse.getXPos();
            private int lastY = mouse.getYPos();

            @Override
            public void onEvent(MoveEvent e) {
                if (mouse.isButtonDown(MouseButton.PRIMARY)) {
                    final Vector3f toRotate = Vector3.zero();
                    toRotate.y += this.lastX - e.getX();
                    toRotate.x += this.lastY - e.getY();
                    toRotate.mul(.3f);
                    camera.getTransform().getRotation().rotate(
                            Angle.degrees(toRotate.x),
                            Angle.degrees(toRotate.y),
                            Angle.degrees(toRotate.z));
                }
                lastX = (int) e.getX();
                lastY = (int) e.getY();
            }
        });
    }
}
