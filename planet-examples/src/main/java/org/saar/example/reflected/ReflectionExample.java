package org.saar.example.reflected;

import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.components.KeyboardMovementComponent;
import org.saar.core.common.components.KeyboardMovementScrollVelocityComponent;
import org.saar.core.common.components.MouseDragRotationComponent;
import org.saar.core.common.flatreflected.*;
import org.saar.core.common.obj.*;
import org.saar.core.common.r3d.*;
import org.saar.core.fog.Fog;
import org.saar.core.fog.FogDistance;
import org.saar.core.light.DirectionalLight;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.RenderContext;
import org.saar.core.renderer.deferred.DeferredRenderNode;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderingPipeline;
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass;
import org.saar.core.renderer.deferred.passes.LightRenderPass;
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass;
import org.saar.core.renderer.forward.passes.FogRenderPass;
import org.saar.core.renderer.reflection.Reflection;
import org.saar.core.renderer.shadow.ShadowsQuality;
import org.saar.core.renderer.shadow.ShadowsRenderNode;
import org.saar.core.renderer.shadow.ShadowsRenderNodeGroup;
import org.saar.core.renderer.shadow.ShadowsRenderingPath;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.gui.UIBlock;
import org.saar.gui.UIDisplay;
import org.saar.gui.UIElement;
import org.saar.gui.UIText;
import org.saar.gui.font.Font;
import org.saar.gui.font.FontLoader;
import org.saar.gui.style.Colours;
import org.saar.gui.style.alignment.AlignmentValues;
import org.saar.gui.style.arrangement.ArrangementValues;
import org.saar.gui.style.coordinate.CoordinateValues;
import org.saar.gui.style.length.LengthValues;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.texture.ColourTexture;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.maths.Angle;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

import java.util.Objects;

public class ReflectionExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final Keyboard keyboard = window.getKeyboard();
        final Mouse mouse = window.getMouse();

        ClearColour.set(.2f, .2f, .2f);

        final UIDisplay uiDisplay = new UIDisplay(window);
        uiDisplay.getStyle().getAlignment().setValue(AlignmentValues.getHorizontal());
        uiDisplay.getStyle().getArrangement().setValue(ArrangementValues.getSpaceAround());

        final UIBlock reflectionUiBlock = new UIBlock();
        reflectionUiBlock.getStyle().getX().set(CoordinateValues.pixelsEnd((30)));
        reflectionUiBlock.getStyle().getY().set(30);
        reflectionUiBlock.getStyle().getWidth().set(300);
        reflectionUiBlock.getStyle().getHeight().set(
                LengthValues.ratio((float) HEIGHT / WIDTH));
        reflectionUiBlock.getStyle().getBorders().set(1);
        reflectionUiBlock.getStyle().getBorderColour().set(Colours.PURPLE);

        uiDisplay.add(reflectionUiBlock);

        final Font font = FontLoader.loadFont(
                FontLoader.DEFAULT_FONT_FAMILY,
                22f, 512, 512, "? .FpsDeltaSpeed:0123456789");

        final UIElement uiTextGroup = new UIElement();
        uiTextGroup.getStyle().getFont().set(font);
        uiTextGroup.getStyle().getFontSize().set(22);
        uiTextGroup.getStyle().getFontColour().set(Colours.WHITE);
        uiTextGroup.getStyle().getAlignment().setValue(AlignmentValues.getVertical());

        final UIText uiFps = new UIText("Fps: ???");
        uiTextGroup.add(uiFps);

        final UIText uiSpeed = new UIText("Speed: ???");
        uiTextGroup.add(uiSpeed);

        final UIText uiDelta = new UIText("Delta: ???");
        uiTextGroup.add(uiDelta);

        uiDisplay.add(uiTextGroup);

        final Projection projection = new ScreenPerspectiveProjection(70f, 1, 300);

        final KeyboardMovementComponent cameraMovementComponent =
                new KeyboardMovementComponent(keyboard, 50f, 50f, 50f);
        final NodeComponentGroup components = new NodeComponentGroup(cameraMovementComponent,
                new KeyboardMovementScrollVelocityComponent(mouse),
                new MouseDragRotationComponent(mouse, -.3f));

        final Camera camera = new Camera(projection, components);
        camera.getTransform().getPosition().set(0, 25, 100);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final ObjNodeBatch objNodeBatch = buildObjNodeBatch();

        final NodeBatch3D nodeBatch3D = buildNodeBatch3D();

        final FlatReflectedModel mirrorModel = buildMirrorModel();
        final FlatReflectedNode mirror = new FlatReflectedNode(mirrorModel);

        final FlatReflectedNodeBatch flatReflectedNodeBatch = new FlatReflectedNodeBatch(mirror);

        final DeferredRenderNodeGroup reflectionRenderNode = new DeferredRenderNodeGroup(objNodeBatch, nodeBatch3D);
        final ShadowsRenderNode shadowsRenderNode = new ShadowsRenderNodeGroup(objNodeBatch, nodeBatch3D);

        final DirectionalLight light = buildDirectionalLight();

        final Camera reflectionCamera = new Camera(camera.getProjection());
        final DeferredRenderingPath reflectionRenderingPath = buildReflectionRenderingPath(
                reflectionCamera, reflectionRenderNode, light);

        final Reflection reflection = new Reflection(mirrorModel.toPlane(),
                camera, reflectionCamera, reflectionRenderingPath);

        final ShadowsRenderingPath shadowsRenderingPath =
                buildShadowsRenderingPath(shadowsRenderNode, light);

        final DeferredRenderNodeGroup renderNode = new DeferredRenderNodeGroup(
                objNodeBatch, nodeBatch3D, flatReflectedNodeBatch);

        final DeferredRenderingPath deferredRenderer = buildRenderingPath(
                camera, renderNode, shadowsRenderingPath, light);

        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            renderNode.update();
            camera.update();
            uiDisplay.update();

            shadowsRenderingPath.render();
            reflection.updateReflectionMap();

            mirrorModel.setReflectionMap(reflection.getReflectionMap());

            deferredRenderer.render().toMainScreen();

            reflectionUiBlock.setTexture(reflection.getReflectionMap());
            uiDisplay.render(new RenderContext(null));

            window.swapBuffers();
            window.pollEvents();

            uiFps.setText(String.format("Fps: %.2f", fps.fps()));
            uiSpeed.setText(String.format("Speed: %.2f", cameraMovementComponent.getVelocity().x()));
            uiDelta.setText(String.format("Delta: %d", (long) (fps.delta() * 1000)));

            fps.update();
        }

        camera.delete();
        uiDisplay.delete();
        reflection.delete();
        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static NodeBatch3D buildNodeBatch3D() {
        final Model3D cubeModel = buildCubeModel();
        final Node3D cube = new Node3D(cubeModel);

        return new NodeBatch3D(cube);
    }

    private static ObjNodeBatch buildObjNodeBatch() {
        final ObjModel cottageModel = buildCottageModel();
        final ObjNode cottage = new ObjNode(cottageModel);

        final ObjModel dragonModel = buildDragonModel();
        final ObjNode dragon = new ObjNode(dragonModel);

        final ObjModel stallModel = buildStallModel();
        final ObjNode stall = new ObjNode(stallModel);

        return new ObjNodeBatch(cottage, dragon, stall);
    }

    private static DeferredRenderingPath buildReflectionRenderingPath(Camera camera, DeferredRenderNode renderNode, DirectionalLight light) {
        final DeferredRenderingPipeline renderPassesPipeline = new DeferredRenderingPipeline(
                new DeferredGeometryPass(renderNode),
                new LightRenderPass(light)
        );

        return new DeferredRenderingPath(camera, renderPassesPipeline);
    }

    private static FlatReflectedModel buildMirrorModel() {
        final FlatReflectedVertex[] vertices = {
                FlatReflected.vertex(Vector3.of(-0.5f, +0.5f, -0.5f)), // 0
                FlatReflected.vertex(Vector3.of(-0.5f, +0.5f, +0.5f)), // 1
                FlatReflected.vertex(Vector3.of(+0.5f, +0.5f, +0.5f)), // 2
                FlatReflected.vertex(Vector3.of(+0.5f, +0.5f, -0.5f)), // 3
        };
        final FlatReflectedMesh mesh = FlatReflected.mesh(vertices, new int[]{0, 1, 2, 0, 2, 3});

        final FlatReflectedModel mirror = new FlatReflectedModel(mesh, Vector3.upward());
        mirror.getTransform().getPosition().set(0, .1f, 0);
        mirror.getTransform().getScale().scale(100, 0, 100);
        return mirror;
    }

    private static ObjModel buildDragonModel() {
        final ObjModel dragonModel = Objects.requireNonNull(loadDragon());
        dragonModel.getTransform().getPosition().set(50, 0, 0);
        return dragonModel;
    }

    private static ObjModel buildCottageModel() {
        return Objects.requireNonNull(loadCottage());
    }

    private static ObjModel buildStallModel() {
        final ObjModel stallModel = Objects.requireNonNull(loadStall());
        stallModel.getTransform().getPosition().set(-50, 0, 0);
        stallModel.getTransform().getRotation().rotate(Angle.degrees(0), Angle.degrees(180), Angle.degrees(0));
        return stallModel;
    }

    private static Model3D buildCubeModel() {
        final Instance3D cubeInstance = R3D.instance();
        cubeInstance.getTransform().getScale().set(10, 10, 10);
        cubeInstance.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = R3D.mesh(new Instance3D[]{cubeInstance},
                ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices);
        return new Model3D(cubeMesh);
    }

    private static DirectionalLight buildDirectionalLight() {
        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);
        return light;
    }

    private static ShadowsRenderingPath buildShadowsRenderingPath(ShadowsRenderNode renderNode, DirectionalLight light) {
        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        return new ShadowsRenderingPath(ShadowsQuality.HIGH,
                shadowProjection, light, renderNode);
    }

    private static DeferredRenderingPath buildRenderingPath(ICamera camera, DeferredRenderNode renderNode,
                                                            ShadowsRenderingPath shadowsRenderingPath, DirectionalLight light) {
        final ReadOnlyTexture2D shadowMap = shadowsRenderingPath.render().getBuffers().getDepth();
        final Fog fog = new Fog(Vector3.of(.2f), 100, 200);

        final DeferredRenderingPipeline renderPassesPipeline = new DeferredRenderingPipeline(
                new DeferredGeometryPass(renderNode),
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, light),
                new ContrastPostProcessor(1.3f),
                new FogRenderPass(fog, FogDistance.XYZ),
                new FxaaPostProcessor()
        );

        return new DeferredRenderingPath(camera, renderPassesPipeline);
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

    private static ObjModel loadStall() {
        try {
            final ObjMesh mesh = Obj.mesh("/assets/stall/stall.model.obj");
            final Texture2D texture = Texture2D.of("/assets/stall/stall.diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadDragon() {
        try {
            final ObjMesh mesh = Obj.mesh("/assets/dragon/dragon.model.obj");
            final ReadOnlyTexture texture = ColourTexture.of(255, 215, 0, 255);
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
