package org.saar.example.normalmapping;

import org.jproperty.ChangeListener;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.components.KeyboardMovementComponent;
import org.saar.core.common.components.KeyboardMovementScrollVelocityComponent;
import org.saar.core.common.components.MouseDragRotationComponent;
import org.saar.core.common.normalmap.NormalMapped;
import org.saar.core.common.normalmap.NormalMappedModel;
import org.saar.core.common.normalmap.NormalMappedNode;
import org.saar.core.common.normalmap.NormalMappedNodeBatch;
import org.saar.core.common.obj.Obj;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.obj.ObjNodeBatch;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.mesh.Mesh;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderingPipeline;
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass;
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass;
import org.saar.core.renderer.shadow.ShadowsQuality;
import org.saar.core.renderer.shadow.ShadowsRenderNode;
import org.saar.core.renderer.shadow.ShadowsRenderNodeGroup;
import org.saar.core.renderer.shadow.ShadowsRenderingPath;
import org.saar.example.ExamplesUtils;
import org.saar.gui.UIChildNode;
import org.saar.gui.UIDisplay;
import org.saar.gui.UIElement;
import org.saar.gui.UIText;
import org.saar.gui.component.UISlider;
import org.saar.gui.style.alignment.AlignmentValues;
import org.saar.gui.style.axisalignment.AxisAlignmentValues;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.texture.ColourTexture;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.maths.transform.Position;

import java.util.Objects;

public class NormalMappingExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        ClearColour.set(0, .7f, .9f);

        final Keyboard keyboard = window.getKeyboard();
        final Mouse mouse = window.getMouse();

        final Projection projection = new ScreenPerspectiveProjection(70f, 1, 1000);

        final KeyboardMovementComponent cameraMovementComponent =
                new KeyboardMovementComponent(keyboard, 50f, 50f, 50f);
        final NodeComponentGroup components = new NodeComponentGroup(cameraMovementComponent,
                new KeyboardMovementScrollVelocityComponent(mouse),
                new MouseDragRotationComponent(mouse, -.3f));

        final Camera camera = new Camera(projection, components);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final NormalMappedNodeBatch normalMappedNodeBatch =
                buildNormalMappedNodeBatch();

        final ObjNodeBatch objNodeBatch = buildObjNodeBatch();

        final NodeBatch3D nodeBatch3D = buildNodeBatch3D();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);

        final ShadowsRenderNode shadowsRenderNode = new ShadowsRenderNodeGroup(
                nodeBatch3D, objNodeBatch, nodeBatch3D, normalMappedNodeBatch);
        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.LOW, shadowProjection, light, shadowsRenderNode);
        final ReadOnlyTexture2D shadowMap = shadowsRenderingPath.render().getBuffers().getDepth();

        final DeferredRenderNodeGroup renderNode = new DeferredRenderNodeGroup(
                nodeBatch3D, normalMappedNodeBatch, objNodeBatch);

        final UIDisplay uiDisplay = buildUIDisplay(window, light);

        final DeferredRenderingPipeline renderPassesPipeline = new DeferredRenderingPipeline(
                new DeferredGeometryPass(renderNode),
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, light),
                new ContrastPostProcessor(1.3f),
                new FxaaPostProcessor(),
                new DeferredGeometryPass(uiDisplay)
        );

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(camera, renderPassesPipeline);

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            camera.update();

            shadowsRenderingPath.render();
            deferredRenderer.render().toMainScreen();

            window.swapBuffers();
            window.pollEvents();

            final long delta = System.currentTimeMillis() - current;

            final float fps = 1000f / delta;
            System.out.print("\r --> " +
                    "Speed: " + String.format("%.2f", cameraMovementComponent.getVelocity().x()) +
                    ", Fps: " + String.format("%.2f", fps) +
                    ", Delta: " + delta);
            current = System.currentTimeMillis();
        }

        camera.delete();
        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static UIDisplay buildUIDisplay(Window window, DirectionalLight light) {
        final UIDisplay uiDisplay = new UIDisplay(window);

        final UIElement uiContainer = new UIElement();
        uiContainer.getStyle().getPadding().set(40);
        uiContainer.getStyle().getAlignment().setValue(AlignmentValues.vertical);

        uiContainer.add(buildUiSlider(light, 0, "x: "));
        uiContainer.add(buildUiSlider(light, 1, "y: "));
        uiContainer.add(buildUiSlider(light, 2, "z: "));

        uiDisplay.add(uiContainer);

        return uiDisplay;
    }

    private static UIChildNode buildUiSlider(DirectionalLight light, int component, String text) {
        final UIElement uiContainer = new UIElement();
        uiContainer.getStyle().getMargin().set(10, 0, 0, 10);
        uiContainer.getStyle().getAxisAlignment().setValue(AxisAlignmentValues.center);
        uiContainer.getStyle().getFontSize().set(24);

        uiContainer.add(new UIText(text));

        final UISlider uiSlider = new UISlider();
        uiSlider.getStyle().getWidth().set(200);
        uiSlider.getStyle().getHeight().set(30);
        uiSlider.getMin().set(-1f);
        uiSlider.getMax().set(1f);
        uiSlider.getDynamicValueProperty().addListener((ChangeListener<? super Number>) e ->
                light.getDirection().setComponent(component, -e.getNewValue().floatValue()));

        uiContainer.add(uiSlider);

        return uiContainer;
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

    private static NormalMappedNodeBatch buildNormalMappedNodeBatch() {
        final NormalMappedModel boulderModel = Objects.requireNonNull(loadBoulder());
        boulderModel.getTransform().getPosition().set(0, 20, 0);
        final NormalMappedNode boulder = new NormalMappedNode(boulderModel);

        final NormalMappedModel barrelModel = Objects.requireNonNull(loadBarrel());
        barrelModel.getTransform().getPosition().set(-20, 20, 0);
        final NormalMappedNode barrel = new NormalMappedNode(barrelModel);

        final NormalMappedModel crateModel = Objects.requireNonNull(loadCrate());
        crateModel.getTransform().getPosition().set(+20, 20, 0);
        crateModel.getTransform().getScale().scale(.05f);
        final NormalMappedNode crate = new NormalMappedNode(crateModel);

        return new NormalMappedNodeBatch(boulder, barrel, crate);
    }

    private static ObjNodeBatch buildObjNodeBatch() {
        final ObjModel cottageModel = Objects.requireNonNull(loadCottage());
        final ObjNode cottage = new ObjNode(cottageModel);

        final ObjModel dragonModel = Objects.requireNonNull(loadDragon());
        dragonModel.getTransform().getPosition().set(50, 0, 0);
        final ObjNode dragon = new ObjNode(dragonModel);

        final ObjModel stallModel = Objects.requireNonNull(loadStall());
        stallModel.getTransform().getRotation().rotateDegrees(0, 180, 0);
        stallModel.getTransform().getPosition().set(-50, 0, 0);
        final ObjNode stall = new ObjNode(stallModel);

        return new ObjNodeBatch(cottage, dragon, stall);
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

    private static ObjModel loadStall() {
        try {
            final Mesh mesh = Obj.mesh("/assets/stall/stall.model.obj");
            final Texture2D texture = Texture2D.of("/assets/stall/stall.diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadDragon() {
        try {
            final Mesh mesh = Obj.mesh("/assets/dragon/dragon.model.obj");
            final ReadOnlyTexture texture = ColourTexture.of(255, 215, 0, 255);
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NormalMappedModel loadBoulder() {
        try {
            final Mesh mesh = NormalMapped.mesh("/assets/boulder/boulder.model.obj");
            final ReadOnlyTexture normalMap = Texture2D.of("/assets/boulder/boulder.normal.png");
            final ReadOnlyTexture texture = Texture2D.of("/assets/boulder/boulder.diffuse.png");
            return new NormalMappedModel(mesh, texture, normalMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NormalMappedModel loadBarrel() {
        try {
            final Mesh mesh = NormalMapped.mesh("/assets/barrel/barrel.model.obj");
            final ReadOnlyTexture normalMap = Texture2D.of("/assets/barrel/barrel.normal.png");
            final ReadOnlyTexture texture = Texture2D.of("/assets/barrel/barrel.diffuse.png");
            return new NormalMappedModel(mesh, texture, normalMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NormalMappedModel loadCrate() {
        try {
            final Mesh mesh = NormalMapped.mesh("/assets/crate/crate.model.obj");
            final ReadOnlyTexture normalMap = Texture2D.of("/assets/crate/crate.normal.png");
            final ReadOnlyTexture texture = Texture2D.of("/assets/crate/crate.diffuse.png");
            return new NormalMappedModel(mesh, texture, normalMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
