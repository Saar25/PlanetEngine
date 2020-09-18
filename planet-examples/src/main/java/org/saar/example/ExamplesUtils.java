package org.saar.example;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.ICamera;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.maths.Angle;
import org.saar.maths.utils.Vector3;

public final class ExamplesUtils {

    private ExamplesUtils() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static void move(ICamera camera, Keyboard keyboard) {
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
}
