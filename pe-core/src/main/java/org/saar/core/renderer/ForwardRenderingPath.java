package org.saar.core.renderer;

import org.saar.core.camera.ICamera;

public class ForwardRenderingPath implements RenderingPath {

    private RenderersHelper helper = RenderersHelper.empty();

    private final ICamera camera;

    public ForwardRenderingPath(ICamera camera) {
        this.camera = camera;
    }

    public void addRenderer(Renderer renderer) {
        this.helper = this.helper.addRenderer(renderer);
    }

    public void removeRenderer(Renderer renderer) {
        this.helper = this.helper.removeRenderer(renderer);
    }

    @Override
    public void render() {
        final RenderContext context = new RenderContextBase(this.camera);

        this.helper.render(context);
    }

    @Override
    public void delete() {
        this.helper.delete();
    }
}
