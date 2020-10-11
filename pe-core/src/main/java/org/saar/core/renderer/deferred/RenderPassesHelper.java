package org.saar.core.renderer.deferred;

import org.saar.core.screen.OffScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class RenderPassesHelper {

    public static RenderPassesHelper empty() {
        return Empty.EMPTY;
    }

    public abstract RenderPassesHelper addRenderPass(RenderPass renderPass);

    public abstract RenderPassesHelper removeRenderPass(RenderPass renderPass);

    public abstract void render(OffScreen screen, OffScreen output, DeferredRenderingBuffers buffers);

    public abstract void delete();

    private static class Empty extends RenderPassesHelper {

        private static final Empty EMPTY = new Empty();

        @Override
        public RenderPassesHelper addRenderPass(RenderPass renderPass) {
            return new Single(renderPass);
        }

        @Override
        public RenderPassesHelper removeRenderPass(RenderPass renderPass) {
            return this;
        }

        @Override
        public void render(OffScreen screen, OffScreen output, DeferredRenderingBuffers buffers) {

        }

        @Override
        public void delete() {

        }
    }

    private static class Single extends RenderPassesHelper {

        private final RenderPass renderPass;

        public Single(RenderPass renderPass) {
            this.renderPass = renderPass;
        }

        @Override
        public RenderPassesHelper addRenderPass(RenderPass renderPass) {
            return new Generic(this.renderPass, renderPass);
        }

        @Override
        public RenderPassesHelper removeRenderPass(RenderPass renderPass) {
            return this.renderPass != renderPass ? this : RenderPassesHelper.empty();
        }

        @Override
        public void render(OffScreen screen, OffScreen output, DeferredRenderingBuffers buffers) {
            screen.setAsDraw();
            this.renderPass.render(buffers);
            screen.copyTo(output);
        }

        @Override
        public void delete() {
            this.renderPass.delete();
        }
    }

    private static class Generic extends RenderPassesHelper {

        private final List<RenderPass> renderPasses;

        public Generic(RenderPass... renderPasses) {
            this.renderPasses = new ArrayList<>(Arrays.asList(renderPasses));
        }

        @Override
        public RenderPassesHelper addRenderPass(RenderPass renderPass) {
            this.renderPasses.add(renderPass);
            return this;
        }

        @Override
        public RenderPassesHelper removeRenderPass(RenderPass renderPass) {
            this.renderPasses.removeIf(renderPass::equals);
            if (this.renderPasses.size() == 0) {
                return RenderPassesHelper.empty();
            } else if (this.renderPasses.size() == 1) {
                return new Single(this.renderPasses.get(0));
            }
            return this;
        }

        @Override
        public void render(OffScreen screen, OffScreen output, DeferredRenderingBuffers buffers) {
            for (RenderPass renderPass : this.renderPasses) {
                screen.setAsDraw();

                renderPass.render(buffers);

                screen.copyTo(output);
            }
        }

        @Override
        public void delete() {
            for (RenderPass renderPass : this.renderPasses) {
                renderPass.delete();
            }
        }
    }

}
