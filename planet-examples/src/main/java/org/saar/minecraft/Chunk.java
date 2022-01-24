package org.saar.minecraft;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Model;
import org.saar.core.mesh.async.FutureMesh;
import org.saar.maths.utils.Maths;
import org.saar.minecraft.chunk.ChunkBounds;
import org.saar.minecraft.chunk.ChunkMeshBuilder;
import org.saar.minecraft.threading.GlThreadQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Chunk implements IChunk, Model {

    private static final int[] shadows = {0, 1, 2, 4, 8};

    private static final Vector3ic[] blockDirection = new Vector3i[]{
            new Vector3i(+1, 0, 0),
            new Vector3i(-1, 0, 0),
            new Vector3i(0, +1, 0),
            new Vector3i(0, -1, 0),
            new Vector3i(0, 0, +1),
            new Vector3i(0, 0, -1),
    };

    private final Vector2i position;
    private final Block[] blocks;

    private final List<BlockContainer> opaqueBlocks = new ArrayList<>();
    private final List<BlockContainer> waterBlocks = new ArrayList<>();
    private final ChunkBounds bounds = new ChunkBounds();
    private final int[] heights;

    private Mesh mesh = null;
    private Mesh waterMesh = null;

    public Chunk(int x, int z) {
        this.position = new Vector2i(x, z);
        this.blocks = new Block[16 * 16 * 256];
        Arrays.fill(this.blocks, Blocks.AIR);
        this.heights = new int[16 * 16];
    }

    private static int index(int x, int y, int z) {
        return ((x & 0xF) << 12) | ((z & 0xF) << 8) | (y & 0xFF);
    }

    private static int index(int x, int z) {
        return ((x & 0xF) << 4) | (z & 0xF);
    }

    private static boolean isInRange(int x, int y, int z) {
        return Maths.isInside(x, 0, 15) &&
                Maths.isInside(z, 0, 15) &&
                Maths.isInside(y, 0, 255);
    }

    public Vector2ic getPosition() {
        return this.position;
    }

    public ChunkBounds getBounds() {
        return this.bounds;
    }

    public int getHeight(int x, int z) {
        if (isInRange(x, 0, z)) {
            final int heightIndex = index(x, z);
            return this.heights[heightIndex];
        }
        return 0;
    }

    private int findHeight(int x, int z) {
        for (int i = 255; i >= 0; i--) {
            if (blocks[index(x, i, z)] != Blocks.AIR) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        if (isInRange(x, y, z)) {
            final int index = index(x, y, z);
            return this.blocks[index];
        }
        return Blocks.AIR;
    }

    @Override
    public void setBlock(int x, int y, int z, Block block) {
        if (isInRange(x, y, z)) {
            final int index = index(x, y, z);
            if (this.blocks[index] != Blocks.AIR) {
                this.opaqueBlocks.removeIf(bc -> bc.
                        getPosition().equals(x, y, z));
            }
            if (block == Blocks.WATER) {
                this.waterBlocks.add(new BlockContainer(x, y, z, block));
            } else if (block != Blocks.AIR) {
                this.opaqueBlocks.add(new BlockContainer(x, y, z, block));
            }
            this.blocks[index] = block;

            this.bounds.addBlock(getPosition().x() * 16 + x, y, getPosition().y() * 16 + z);

            final int heightIndex = index(x, z);
            if (block == Blocks.AIR && this.heights[heightIndex] == y) {
                this.heights[heightIndex] = findHeight(x, z);
            } else if (this.heights[heightIndex] < y) {
                this.heights[heightIndex] = y;
            }
        }
    }

    private Block[] blocksAround(World world, int x, int y, int z) {
        final int wx = x + getPosition().x() * 16;
        final int wz = z + getPosition().y() * 16;

        final Block xPos = x == 0xF ? world.getBlock(wx + 1, y, wz) : getBlock(x + 1, y, z);
        final Block xNeg = x == 0x0 ? world.getBlock(wx - 1, y, wz) : getBlock(x - 1, y, z);
        final Block yPos = getBlock(x, y + 1, z);
        final Block yNeg = getBlock(x, y - 1, z);
        final Block zPos = z == 0xF ? world.getBlock(wx, y, wz + 1) : getBlock(x, y, z + 1);
        final Block zNeg = z == 0x0 ? world.getBlock(wx, y, wz - 1) : getBlock(x, y, z - 1);
        return new Block[]{xPos, xNeg, yPos, yNeg, zPos, zNeg};
    }

    public void updateMesh(World world) {
        if (this.mesh != null) {
            final Mesh mesh = this.mesh;
            GlThreadQueue.getInstance().supply(mesh::delete);
        }
        this.mesh = FutureMesh.unloaded(writeMesh(world, this.opaqueBlocks));

        if (this.waterMesh != null) {
            final Mesh mesh = this.waterMesh;
            GlThreadQueue.getInstance().supply(mesh::delete);
        }
        this.waterMesh = FutureMesh.unloaded(writeMesh(world, this.waterBlocks));
    }

    @NotNull
    @Override
    public Mesh getMesh() {
        return this.mesh;
    }

    @Override
    public void draw() {
        drawOpaque();
        drawWater();
    }

    public void drawOpaque() {
        if (this.mesh != null) {
            this.mesh.draw();
        }
    }

    public void drawWater() {
        if (this.waterMesh != null) {
            this.waterMesh.draw();
        }
    }

    @Override
    public void delete() {
        if (this.mesh != null) {
            this.mesh.delete();
        }
        if (this.waterMesh != null) {
            this.waterMesh.delete();
        }
    }

    private CompletableFuture<ChunkMeshBuilder> writeMesh(World world, List<BlockContainer> blocks) {
        final List<BlockFaceContainer> blockFaceContainers = new ArrayList<>();
        for (BlockContainer b : blocks) {
            final Block[] around = blocksAround(world, b.getX(), b.getY(), b.getZ());

            for (int i = 0; i < around.length; i++) {
                if (around[i].isTransparent() && !(around[i] == Blocks.WATER && b.getBlock() == Blocks.WATER)) {
                    final BlockFaceContainer face = new BlockFaceContainer(
                            b.getX(), b.getY(), b.getZ(), b.getBlock(), i);
                    blockFaceContainers.add(face);
                }
            }
        }

        final int faceCount = blockFaceContainers.size() * 6;
        return GlThreadQueue.getInstance().supply(() -> {
            final ChunkMeshBuilder builder = ChunkMeshBuilder.fixed(faceCount);
            for (BlockFaceContainer b : blockFaceContainers) {
                final int shadow = getShadow(world, b);
                final int faceId = b.getBlock().getFaces().faceId(b.getDirection());
                builder.addFace(b.getX(), b.getY(), b.getZ(), faceId, b.getDirection(), shadow);
            }
            return builder;
        });
    }

    private int getShadow(World world, BlockFaceContainer b) {
        final Vector3ic direction = blockDirection[b.getDirection()];
        final int x = b.getX() + direction.x() + getPosition().x() * 16;
        final int y = b.getY() + direction.y();
        final int z = b.getZ() + direction.z() + getPosition().y() * 16;

        int shadow = 0;
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (world.getHeight(x + i, z + j) >= y) {
                    final int pi = 2 - Math.abs(i);
                    final int pj = 2 - Math.abs(j);
                    shadow += shadows[pi + pj];
                }
            }
        }
        return shadow * 8 / 40;
    }
}
