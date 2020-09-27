package org.saar.core.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class RenderersHelper {

    public static RenderersHelper empty() {
        return Empty.EMPTY;
    }

    public abstract RenderersHelper addRenderer(Renderer renderer);

    public abstract RenderersHelper removeRenderer(Renderer renderer);

    public abstract void render(RenderContext context);

    public abstract void delete();

    private static class Empty extends RenderersHelper {

        private static final Empty EMPTY = new Empty();

        @Override
        public RenderersHelper addRenderer(Renderer renderer) {
            return new Single(renderer);
        }

        @Override
        public RenderersHelper removeRenderer(Renderer renderer) {
            return this;
        }

        @Override
        public void render(RenderContext context) {

        }

        @Override
        public void delete() {

        }
    }

    private static class Single extends RenderersHelper {

        private final Renderer renderer;

        public Single(Renderer renderer) {
            this.renderer = renderer;
        }

        @Override
        public RenderersHelper addRenderer(Renderer renderer) {
            return new Generic(this.renderer, renderer);
        }

        @Override
        public RenderersHelper removeRenderer(Renderer renderer) {
            return this.renderer != renderer ? this : RenderersHelper.empty();
        }

        @Override
        public void render(RenderContext context) {
            this.renderer.render(context);
        }

        @Override
        public void delete() {
            this.renderer.delete();
        }
    }

    private static class Generic extends RenderersHelper {

        private final List<Renderer> renderers;

        public Generic(Renderer... renderers) {
            this.renderers = new ArrayList<>(Arrays.asList(renderers));
        }

        @Override
        public RenderersHelper addRenderer(Renderer renderer) {
            this.renderers.add(renderer);
            return this;
        }

        @Override
        public RenderersHelper removeRenderer(Renderer renderer) {
            this.renderers.removeIf(renderer::equals);
            if (this.renderers.size() == 0) {
                return RenderersHelper.empty();
            } else if (this.renderers.size() == 1) {
                return new Single(this.renderers.get(0));
            }
            return this;
        }

        @Override
        public void render(RenderContext context) {
            for (Renderer renderer : this.renderers) {
                renderer.render(context);
            }
        }

        @Override
        public void delete() {
            for (Renderer renderer : this.renderers) {
                renderer.delete();
            }
        }
    }

}
