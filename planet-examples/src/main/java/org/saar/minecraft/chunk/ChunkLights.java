package org.saar.minecraft.chunk;

import org.saar.minecraft.Block;
import org.saar.minecraft.Chunk;
import org.saar.minecraft.EmptyChunk;
import org.saar.minecraft.LightPropagation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ChunkLights {

    private final Chunk chunk;

    private final byte[] lights = new byte[16 * 16 * 256];

    public ChunkLights(Chunk chunk) {
        this.chunk = chunk;
        Arrays.fill(this.lights, (byte) 0xFF);
    }

    private static int index(int x, int y, int z) {
        return ((x & 0xF) << 12) | ((z & 0xF) << 8) | (y & 0xFF);
    }

    private int calculateLight(int x, int y, int z) {
        final Block block = this.chunk.getBlock(x, y, z);
        final LightPropagation p = block.getLightPropagation();
        if (block.isTransparent()) {
            int max = 0;
            max = Math.max(max, this.chunk.getLight(x, y + 1, z) - p.getDown());
            max = Math.max(max, this.chunk.getLight(x, y - 1, z) - p.getSide());
            max = Math.max(max, this.chunk.getLight(x - 1, y, z) - p.getSide());
            max = Math.max(max, this.chunk.getLight(x + 1, y, z) - p.getSide());
            max = Math.max(max, this.chunk.getLight(x, y, z - 1) - p.getSide());
            max = Math.max(max, this.chunk.getLight(x, y, z + 1) - p.getSide());
            return max;
        }
        return 0;
    }

    public void recalculateLight() {
        Arrays.fill(this.lights, (byte) 0);

        final Queue<LightNode> spreadBfs = new LinkedList<>();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (this.chunk.getBlock(x, 0xFF, z).isTransparent()) {
                    spreadBfs.add(new LightNode(x, 0xFF, z, 0xFF));
                }
            }
        }

        spreadLight(spreadBfs);
    }

    public void updateLight(int x, int y, int z) {
        final int light = calculateLight(x, y, z);
        updateLight(x, y, z, light);
    }

    private void updateLight(int x, int y, int z, int light) {
        final Queue<LightNode> spreadBfs = new LinkedList<>();
        final Queue<LightNode> removeBfs = new LinkedList<>();

        if (this.chunk.getBlock(x, y, z).isTransparent()) {
            spreadBfs.add(new LightNode(x, y, z, light));
            this.chunk.setLight(x, y, z, (byte) light);
        } else {
            removeBfs.add(new LightNode(x, y, z, getLight(x, y, z)));
            this.chunk.setLight(x, y, z, (byte) 0);
        }

        removeLight(spreadBfs, removeBfs);
        spreadLight(spreadBfs);
    }


    private void removeLight(Queue<LightNode> addBfs, Queue<LightNode> remBfs) {
        while (!remBfs.isEmpty()) {
            final LightNode n = remBfs.poll();
            removeLightDown(addBfs, remBfs, n.x, n.y - 1, n.z, n);
            removeLightSide(addBfs, remBfs, n.x, n.y + 1, n.z, n);
            removeLightSide(addBfs, remBfs, n.x + 1, n.y, n.z, n);
            removeLightSide(addBfs, remBfs, n.x - 1, n.y, n.z, n);
            removeLightSide(addBfs, remBfs, n.x, n.y, n.z + 1, n);
            removeLightSide(addBfs, remBfs, n.x, n.y, n.z - 1, n);
        }
    }

    private void removeLightDown(Queue<LightNode> addBfs, Queue<LightNode> remBfs, int x, int y, int z, LightNode n) {
        final Block block = this.chunk.getBlock(x, y, z);
        final LightPropagation p = block.getLightPropagation();
        final int spread = Math.max(n.level - p.getDown(), 0);
        removeLight(addBfs, remBfs, x, y, z, spread);
    }

    private void removeLightSide(Queue<LightNode> addBfs, Queue<LightNode> remBfs, int x, int y, int z, LightNode n) {
        final Block block = this.chunk.getBlock(x, y, z);
        final LightPropagation p = block.getLightPropagation();
        final int spread = Math.max(n.level - p.getSide(), 0);
        removeLight(addBfs, remBfs, x, y, z, spread);
    }

    private void spreadLight(Queue<LightNode> spreadBfs) {
        while (!spreadBfs.isEmpty()) {
            final LightNode n = spreadBfs.poll();
            spreadLightDown(spreadBfs, n.x, n.y - 1, n.z, n);
            spreadLightSide(spreadBfs, n.x, n.y + 1, n.z, n);
            spreadLightSide(spreadBfs, n.x + 1, n.y, n.z, n);
            spreadLightSide(spreadBfs, n.x - 1, n.y, n.z, n);
            spreadLightSide(spreadBfs, n.x, n.y, n.z + 1, n);
            spreadLightSide(spreadBfs, n.x, n.y, n.z - 1, n);
        }
    }

    private void spreadLightDown(Queue<LightNode> addBfs, int x, int y, int z, LightNode n) {
        final Block block = this.chunk.getBlock(x, y, z);
        final LightPropagation p = block.getLightPropagation();
        final int spread = Math.max(n.level - p.getDown(), 0);
        spreadLight(addBfs, x, y, z, spread);
    }

    private void spreadLightSide(Queue<LightNode> addBfs, int x, int y, int z, LightNode n) {
        final Block block = this.chunk.getBlock(x, y, z);
        final LightPropagation p = block.getLightPropagation();
        final int spread = Math.max(n.level - p.getSide(), 0);
        spreadLight(addBfs, x, y, z, spread);
    }

    private void spreadLight(Queue<LightNode> addBfs, int x, int y, int z, int spread) {
        if (y < 0 || y > 255 || this.chunk.getRelativeChunk(x, z) == EmptyChunk.getInstance()) return;
        if (spread < 0) return;

        final int light = this.chunk.getLight(x, y, z);
        final Block block = this.chunk.getBlock(x, y, z);
        if (block.isTransparent() && light < spread) {
            this.chunk.setLight(x, y, z, (byte) spread);

            addBfs.add(new LightNode(x, y, z, spread));
        }
    }

    private void removeLight(Queue<LightNode> addBfs, Queue<LightNode> remBfs, int x, int y, int z, int spread) {
        if (y < 0 || y > 255 || this.chunk.getRelativeChunk(x, z) == EmptyChunk.getInstance()) return;

        final int light = this.chunk.getLight(x, y, z);
        if (light != 0 && light <= spread) {
            this.chunk.setLight(x, y, z, (byte) 0);

            remBfs.add(new LightNode(x, y, z, spread));
        } else if (light > spread) {
            addBfs.add(new LightNode(x, y, z, light));
        }
    }

    public int getLight(int x, int y, int z) {
        final int index = index(x, y, z);
        return getLight(index);
    }

    private int getLight(int index) {
        return this.lights[index] & 0xFF;
    }

    public void setLight(int x, int y, int z, byte level) {
        final int index = index(x, y, z);
        this.lights[index] = level;
    }

    private static class LightNode {
        public final int x;
        public final int y;
        public final int z;
        public final int level;

        public LightNode(int x, int y, int z, int level) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.level = level;
        }

        @Override
        public String toString() {
            return "LightNode{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", level=" + level +
                    '}';
        }
    }
}
