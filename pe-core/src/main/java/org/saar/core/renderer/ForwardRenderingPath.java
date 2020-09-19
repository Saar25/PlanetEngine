package org.saar.core.renderer;

public class ForwardRenderingPath implements RenderingPath {

    private RenderersHelper helper = RenderersHelper.empty();

    public void addRenderer(Renderer renderer) {
        this.helper = this.helper.addRenderer(renderer);
    }

    public void removeRenderer(Renderer renderer) {
        this.helper = this.helper.removeRenderer(renderer);
    }

    @Override
    public void render() {
        this.helper.render();
    }

    @Override
    public void delete() {
        this.helper.delete();
    }
}
