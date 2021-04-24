package org.saar.example.reflected;

import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.flatreflected.*;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.r3d.*;
import org.saar.core.light.DirectionalLight;
import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.Renderer;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.RenderPassesPipeline;
import org.saar.core.renderer.deferred.light.LightRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsQuality;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderingPath;
import org.saar.core.renderer.reflection.Reflection;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.example.MyScreenPrototype;
import org.saar.gui.UIBlock;
import org.saar.gui.UIComponent;
import org.saar.gui.UIDisplay;
import org.saar.gui.render.UIRenderer;
import org.saar.gui.style.Colours;
import org.saar.gui.style.value.LengthValues;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.ColourTexture;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.Angle;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

import java.util.Objects;

public class ReflectionExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    private static float scrollSpeed = 50f;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        GlUtils.setClearColour(.1f, .1f, .1f);

        final UIDisplay uiDisplay = new UIDisplay(window);

        final UIBlock reflectionUiBlock = new UIBlock();
        reflectionUiBlock.getStyle().getX().set(30);
        reflectionUiBlock.getStyle().getY().set(30);
        reflectionUiBlock.getStyle().getWidth().set(300);
        reflectionUiBlock.getStyle().getHeight().set(
                LengthValues.ratio((float) HEIGHT / WIDTH));
        reflectionUiBlock.getStyle().getBorders().set(1);
        reflectionUiBlock.getStyle().getBorderColour().set(Colours.PURPLE);

        final UIComponent uiComponent = new UIComponent();
        uiComponent.add(reflectionUiBlock);

        uiDisplay.add(uiComponent);

        final UIRenderer uiRenderer = new UIRenderer(uiDisplay);

        final Camera camera = buildCamera();

        final Renderer objRenderer = new ObjDeferredRenderer(
                buildCottageModel(), buildDragonModel(), buildStallModel());

        final Renderer renderer3D = new DeferredRenderer3D(buildCubeModel());

        final FlatReflectedModel mirror = buildMirrorModel();

        final RenderersGroup baseRenderersGroup = new RenderersGroup(objRenderer, renderer3D);

        final Camera reflectionCamera = new Camera(camera.getProjection());
        final RenderingPath reflectionRenderingPath = buildReflectionRenderingPath(
                reflectionCamera, baseRenderersGroup);

        final Reflection reflection = new Reflection(mirror.toPlane(), camera,
                reflectionCamera, reflectionRenderingPath);

        final DirectionalLight light = buildDirectionalLight();

        final ShadowsRenderingPath shadowsRenderingPath =
                buildShadowsRenderingPath(baseRenderersGroup, light);

        final Renderer flatReflectedDeferredRenderer =
                new FlatReflectedDeferredRenderer(mirror);

        final RenderersGroup renderersGroup = new RenderersGroup(
                flatReflectedDeferredRenderer, renderer3D, objRenderer);

        final RenderingPath deferredRenderer = buildRenderingPath(
                camera, renderersGroup, shadowsRenderingPath, light);

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);
        mouse.addScrollListener(e -> {
            scrollSpeed += e.getOffset();
            scrollSpeed = Math.max(scrollSpeed, 1);
        });

        final PostProcessingPipeline postProcessingPipeline = new PostProcessingPipeline(
                new ContrastPostProcessor(1.3f),
                new FxaaPostProcessor()
        );

        final Keyboard keyboard = window.getKeyboard();

        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            reflection.updateReflectionMap();

            mirror.setReflectionMap(reflection.getReflectionMap());
            final ReadOnlyTexture output = deferredRenderer.render().toTexture();
            postProcessingPipeline.process(output).toMainScreen();

            reflectionUiBlock.setTexture(reflection.getReflectionMap());
            uiRenderer.render(new RenderContextBase(camera));

            window.update(true);
            window.pollEvents();

            final long delta = (long) (fps.delta() * 1000);
            ExamplesUtils.move(camera, keyboard, delta, scrollSpeed);

            System.out.print("\r --> " +
                    "Speed: " + String.format("%.2f", scrollSpeed) +
                    ", Fps: " + String.format("%.2f", fps.fps()) +
                    ", Delta: " + delta);

            fps.update();
        }

        postProcessingPipeline.delete();
        reflection.delete();
        renderersGroup.delete();
        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

    private static RenderingPath buildReflectionRenderingPath(Camera camera, RenderersGroup renderersGroup) {
        final MyScreenPrototype reflectionScreenPrototype = new MyScreenPrototype();

        final RenderPassesPipeline renderPassesPipeline =
                new RenderPassesPipeline(new LightRenderPass());

        return new DeferredRenderingPath(reflectionScreenPrototype,
                camera, renderersGroup, renderPassesPipeline);
    }

    private static FlatReflectedModel buildMirrorModel() {
        final FlatReflectedVertex[] vertices = {
                FlatReflected.vertex(Vector3.of(-0.5f, +0.5f, -0.5f)), // 0
                FlatReflected.vertex(Vector3.of(-0.5f, +0.5f, +0.5f)), // 1
                FlatReflected.vertex(Vector3.of(+0.5f, +0.5f, +0.5f)), // 2
                FlatReflected.vertex(Vector3.of(+0.5f, +0.5f, -0.5f)), // 3
        };
        final FlatReflectedMesh mesh = FlatReflectedMesh.load(vertices, new int[]{3, 2, 1, 3, 1, 0});

        final FlatReflectedModel mirror = new FlatReflectedModel(mesh, Vector3.upward());
        mirror.getTransform().getPosition().set(0, .1f, 0);
        mirror.getTransform().getScale().scale(100, 0, 100);
        return mirror;
    }

    private static Camera buildCamera() {
        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);

        final Camera camera = new Camera(projection);
        camera.getTransform().getPosition().set(-50, 25, 50);
        camera.getTransform().lookAt(Position.of(0, 0, 0));
        return camera;
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
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices,
                ExamplesUtils.cubeIndices, new Instance3D[]{cubeInstance});
        return new Model3D(cubeMesh);
    }

    private static DirectionalLight buildDirectionalLight() {
        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);
        return light;
    }

    private static ShadowsRenderingPath buildShadowsRenderingPath(RenderersGroup shadowsRenderersGroup, DirectionalLight light) {
        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        return new ShadowsRenderingPath(ShadowsQuality.HIGH,
                shadowProjection, light, shadowsRenderersGroup);
    }

    private static RenderingPath buildRenderingPath(ICamera camera, RenderersGroup renderersGroup,
                                                    ShadowsRenderingPath shadowsRenderingPath, DirectionalLight light) {
        final ReadOnlyTexture shadowMap = shadowsRenderingPath.render().toTexture();

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final RenderPassesPipeline renderPassesPipeline = new RenderPassesPipeline(
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, light)
        );

        return new DeferredRenderingPath(screenPrototype, camera, renderersGroup, renderPassesPipeline);
    }

    private static ObjModel loadCottage() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadStall() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/stall/stall.model.obj");
            final Texture2D texture = Texture2D.of("/assets/stall/stall.diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadDragon() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/dragon/dragon.model.obj");
            final ReadOnlyTexture texture = ColourTexture.of(255, 215, 0, 255);
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
