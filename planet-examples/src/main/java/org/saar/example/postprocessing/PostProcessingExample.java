package org.saar.example.postprocessing;

import org.saar.core.camera.Camera;
import org.saar.core.common.r2d.*;
import org.saar.core.postprocessing.PostProcessingBuffers;
import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.postprocessing.processors.GaussianBlurPostProcessor;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.renderpass.RenderPassContext;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.SimpleScreen;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

public class PostProcessingExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        ClearColour.set(.2f, .2f, .2f);

        final Camera camera = new Camera(null);

        final Model2D model = buildModel2D();
        final Renderer2D renderer = Renderer2D.INSTANCE;

        final SimpleScreen screen = new SimpleScreen(Fbo.create(WIDTH, HEIGHT));

        final Texture colourTexture = Texture.create(TextureTarget.TEXTURE_2D);
        final ColourScreenImage image = new ColourScreenImage(ColourAttachment.withTexture(
                0, colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE));
        screen.addScreenImage(image);
        screen.setDrawImages(image);
        screen.setReadImages(image);

        final PostProcessingPipeline pipeline = new PostProcessingPipeline(
                new ContrastPostProcessor(1.8f),
                new GaussianBlurPostProcessor(11, 2)
        );

        window.addResizeListener(e -> screen.resize(
                e.getWidth().getAfter(), e.getHeight().getAfter()));

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            screen.setAsDraw();

            GlUtils.clearColourAndDepthBuffer();
            renderer.render(new RenderContextBase(null), model);

            pipeline.process(new RenderPassContext(camera),
                    new PostProcessingBuffers(colourTexture));

            screen.copyTo(MainScreen.INSTANCE);

            window.update(true);
            window.pollEvents();
        }

        pipeline.delete();
        screen.delete();
        model.delete();
        window.destroy();
    }

    private static Model2D buildModel2D() {
        final float s = 0.7f;
        final Vertex2D[] vertices = {
                R2D.vertex(Vector2.of(-s, -s), Vector3.of(+0.0f, +0.0f, +0.5f)),
                R2D.vertex(Vector2.of(-s, +s), Vector3.of(+0.0f, +1.0f, +0.5f)),
                R2D.vertex(Vector2.of(+s, +s), Vector3.of(+1.0f, +1.0f, +0.5f)),
                R2D.vertex(Vector2.of(+s, -s), Vector3.of(+1.0f, +0.0f, +0.5f))
        };
        final int[] indices = {0, 1, 2, 0, 2, 3};

        final Mesh2D mesh = Mesh2D.load(vertices, indices);
        return new Model2D(mesh);
    }

}
