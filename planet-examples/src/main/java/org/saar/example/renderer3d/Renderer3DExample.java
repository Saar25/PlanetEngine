package org.saar.example.renderer3d;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.components.KeyboardMovementComponent;
import org.saar.core.common.components.KeyboardRotationComponent;
import org.saar.core.common.r3d.*;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.buffer.MeshBufferBuilder;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.renderer.RenderContext;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.lwjgl.opengl.vbo.VboUsage;
import org.saar.lwjgl.util.buffer.FixedBufferBuilder;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Quaternion;

public class Renderer3DExample {

    private static final boolean optimizeMesh = false;
    private static final boolean singleBatch = true;

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static final int CUBES = 100_000;
    private static final int AREA = 1000;
    private static final int BATCHES = singleBatch ? 1 : 10_000;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        final Keyboard keyboard = window.getKeyboard();

        final Camera camera = buildCamera(keyboard);

        final Model3D[] models = models();
        final Renderer3D renderer = Renderer3D.INSTANCE;

        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            camera.update();

            renderer.render(new RenderContext(camera), models);

            window.swapBuffers();
            window.pollEvents();

            System.out.print("\rFps: " + fps.fps());

            fps.update();
        }

        camera.delete();
        renderer.delete();
        window.destroy();
    }

    private static Camera buildCamera(Keyboard keyboard) {
        final Projection projection = new ScreenPerspectiveProjection(70f, 1, 1000);

        final NodeComponentGroup components = new NodeComponentGroup(
                new KeyboardMovementComponent(keyboard, 50f, 50f, 50f),
                new KeyboardRotationComponent(keyboard, 50f));

        final Camera camera = new Camera(projection, components);

        camera.getTransform().getPosition().set(0, 0, -1000);
        camera.getTransform().lookAt(Position.of(0, 0, 0));
        return camera;
    }

    private static Model3D[] models() {
        final int cubesPerBatch = CUBES / BATCHES;
        final Model3D[] batches = new Model3D[BATCHES];
        final Instance3D[] instances = new Instance3D[cubesPerBatch];
        for (int i = 0; i < BATCHES; i++) {
            for (int j = 0; j < cubesPerBatch; j++) {
                final Instance3D newNode = R3D.instance();
                final float x = (float) (Math.random() * AREA - AREA / 2);
                final float y = (float) (Math.random() * AREA - AREA / 2);
                final float z = (float) (Math.random() * AREA - AREA / 2);
                newNode.getTransform().getPosition().set(x, y, z);
                newNode.getTransform().getRotation().set(Quaternion.of(
                        (float) Math.random(), (float) Math.random(),
                        (float) Math.random(), (float) Math.random()).normalize());
                instances[j] = newNode;
            }
            final Mesh mesh = optimizeMesh
                    ? optimizedMesh(instances)
                    : unoptimizedMesh(instances);
            batches[i] = new Model3D(mesh);
        }
        return batches;
    }

    private static Mesh optimizedMesh(Instance3D[] instances) {
        final MeshBufferBuilder vertexBufferBuilder = new MeshBufferBuilder(
                new FixedBufferBuilder(ExamplesUtils.cubeVertices.length * 9 * 4),
                VboUsage.STATIC_DRAW);

        final MeshBufferBuilder instanceBufferBuilder = new MeshBufferBuilder(
                new FixedBufferBuilder(instances.length * 16 * 4),
                VboUsage.STATIC_DRAW);

        final MeshBufferBuilder indexBufferBuilder = new MeshBufferBuilder(
                new FixedBufferBuilder(ExamplesUtils.cubeIndices.length * 4),
                VboUsage.STATIC_DRAW);

        final MeshBuilder3D meshBuilder3D = new MeshBuilder3D(ExamplesUtils.cubeIndices.length, instances.length,
                vertexBufferBuilder, vertexBufferBuilder, vertexBufferBuilder,
                instanceBufferBuilder, indexBufferBuilder);

        for (Vertex3D vertex : ExamplesUtils.cubeVertices) {
            meshBuilder3D.getWriter().writeVertex(vertex);
        }
        for (Instance3D instance : instances) {
            meshBuilder3D.getWriter().writeInstance(instance);
        }
        for (int index : ExamplesUtils.cubeIndices) {
            meshBuilder3D.getWriter().writeIndex(index);
        }

        return meshBuilder3D.load();
    }

    private static Mesh unoptimizedMesh(Instance3D[] instances) {
        final MeshBufferBuilder positionBufferBuilder = new MeshBufferBuilder(
                new FixedBufferBuilder(ExamplesUtils.cubeVertices.length * 3 * 4),
                VboUsage.STATIC_DRAW);

        final MeshBufferBuilder normalBufferBuilder = new MeshBufferBuilder(
                new FixedBufferBuilder(ExamplesUtils.cubeVertices.length * 3 * 4),
                VboUsage.STATIC_DRAW);

        final MeshBufferBuilder colourBufferBuilder = new MeshBufferBuilder(
                new FixedBufferBuilder(ExamplesUtils.cubeVertices.length * 3 * 4),
                VboUsage.STATIC_DRAW);

        final MeshBufferBuilder instanceBufferBuilder = new MeshBufferBuilder(
                new FixedBufferBuilder(instances.length * 16 * 4),
                VboUsage.STATIC_DRAW);

        final MeshBufferBuilder indexBufferBuilder = new MeshBufferBuilder(
                new FixedBufferBuilder(ExamplesUtils.cubeIndices.length * 4),
                VboUsage.STATIC_DRAW);

        final MeshBuilder3D meshBuilder3D = new MeshBuilder3D(ExamplesUtils.cubeIndices.length, instances.length,
                positionBufferBuilder, normalBufferBuilder, colourBufferBuilder,
                instanceBufferBuilder, indexBufferBuilder);

        for (Vertex3D vertex : ExamplesUtils.cubeVertices) {
            meshBuilder3D.getWriter().writeVertex(vertex);
        }
        for (Instance3D instance : instances) {
            meshBuilder3D.getWriter().writeInstance(instance);
        }
        for (int index : ExamplesUtils.cubeIndices) {
            meshBuilder3D.getWriter().writeIndex(index);
        }

        return meshBuilder3D.load();
    }
}
