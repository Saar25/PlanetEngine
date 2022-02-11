package org.saar.minecraft;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.joml.Vector3ic;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Model;
import org.saar.core.mesh.async.FutureMesh;
import org.saar.minecraft.chunk.*;
import org.saar.minecraft.threading.GlThreadQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Chunk implements IChunk, Model {

    private final World world;
    private final Vector2i position;

    private final ChunkBlocks blocks = new ChunkBlocks();
    private final ChunkHeights heights = new ChunkHeights(this);
    private final ChunkLights lights = new ChunkLights(this);
    private final ChunkBounds bounds = new ChunkBounds();

    private Mesh mesh = null;
    private Mesh waterMesh = null;
    private boolean meshUpdateNeeded = false;

    public Chunk(World world, int x, int z) {
        this.world = world;
        this.position = new Vector2i(x, z);
    }

    public Vector2ic getPosition() {
        return this.position;
    }

    public ChunkBounds getBounds() {
        return this.bounds;
    }

    @Override
    public int getHeight(int x, int z) {
        if (x < 0 || x > 0xF || z < 0 || z > 0xF) {
            final int wx = x + getPosition().x() * 16;
            final int wz = z + getPosition().y() * 16;
            return this.world.getHeight(wx, wz);
        }
        return this.heights.getHeight(x, z);
    }

    public void updateLight(int x, int y, int z, int light) {
        if (y < 0 || y > 0xFF) return;
        if (x < 0 || x > 0xF || z < 0 || z > 0xF) {
            final int wx = x + getPosition().x() * 16;
            final int wz = z + getPosition().y() * 16;
            this.world.updateLight(wx, y, wz, light);
        } else {
            this.meshUpdateNeeded = true;
            this.lights.updateLight(x, y, z, light);
        }
    }

    @Override
    public int getLight(int x, int y, int z) {
        if (y < 0) return 0;
        if (y > 0xFF) return 0xF;
        if (x < 0 || x > 0xF || z < 0 || z > 0xF) {
            final int wx = x + getPosition().x() * 16;
            final int wz = z + getPosition().y() * 16;
            return this.world.getLight(wx, y, wz);
        }
        return this.lights.getLight(x, y, z);
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        if (y < 0 || y > 0xFF) return Blocks.AIR;
        if (x < 0 || x > 0xF || z < 0 || z > 0xF) {
            final int wx = x + getPosition().x() * 16;
            final int wz = z + getPosition().y() * 16;
            return this.world.getBlock(wx, y, wz);
        }
        return this.blocks.getBlock(x, y, z);
    }

    @Override
    public void setBlock(int x, int y, int z, Block block) {
        if (y < 0 || y > 0xFF) return;
        if (x < 0 || x > 0xF || z < 0 || z > 0xF) {
            final int wx = x + getPosition().x() * 16;
            final int wz = z + getPosition().y() * 16;
            this.world.setBlock(wx, y, wz, block);
        } else {
            this.blocks.setBlock(x, y, z, block);

            final int wx = x + getPosition().x() * 16;
            final int wz = z + getPosition().y() * 16;
            this.bounds.addBlock(wx, y, wz);

            if (block != Blocks.AIR) {
                this.heights.addBlock(x, y, z);
            } else {
                this.heights.removeBlock(x, y, z);
            }

            this.lights.updateLight(x, y, z);

            this.meshUpdateNeeded = true;
            if (x == 0) {
                final IChunk chunk = world.getChunk(getPosition().x() - 1, getPosition().y());
                if (chunk instanceof Chunk) ((Chunk) chunk).meshUpdateNeeded = true;
            } else if (x == 0xF) {
                final IChunk chunk = world.getChunk(getPosition().x() + 1, getPosition().y());
                if (chunk instanceof Chunk) ((Chunk) chunk).meshUpdateNeeded = true;
            }
            if (z == 0) {
                final IChunk chunk = world.getChunk(getPosition().x(), getPosition().y() - 1);
                if (chunk instanceof Chunk) ((Chunk) chunk).meshUpdateNeeded = true;
            } else if (z == 0xF) {
                final IChunk chunk = world.getChunk(getPosition().x(), getPosition().y() + 1);
                if (chunk instanceof Chunk) ((Chunk) chunk).meshUpdateNeeded = true;
            }
        }
    }

    private Block[] blocksAround(int x, int y, int z) {
        final Block xPos = getBlock(x + 1, y, z);
        final Block xNeg = getBlock(x - 1, y, z);
        final Block yPos = getBlock(x, y + 1, z);
        final Block yNeg = getBlock(x, y - 1, z);
        final Block zPos = getBlock(x, y, z + 1);
        final Block zNeg = getBlock(x, y, z - 1);
        return new Block[]{xPos, xNeg, yPos, yNeg, zPos, zNeg};
    }

    public void updateMesh() {
        if (!this.meshUpdateNeeded) return;
        this.meshUpdateNeeded = false;

        if (this.mesh != null) {
            final Mesh mesh = this.mesh;
            GlThreadQueue.getInstance().supply(mesh::delete);
        }
        this.mesh = FutureMesh.unloaded(writeMesh(this.blocks.getOpaque()));

        if (this.waterMesh != null) {
            final Mesh mesh = this.waterMesh;
            GlThreadQueue.getInstance().supply(mesh::delete);
        }
        this.waterMesh = FutureMesh.unloaded(writeMesh(this.blocks.getWater()));
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

    private CompletableFuture<ChunkMeshBuilder> writeMesh(List<BlockContainer> blocks) {
        final List<BlockFaceContainer> blockFaceContainers = new ArrayList<>();
        for (BlockContainer b : blocks) {
            final Block[] around = blocksAround(b.getX(), b.getY(), b.getZ());

            for (int i = 0; i < around.length; i++) {
                if (around[i].isTransparent() && !(around[i] == Blocks.WATER && b.getBlock() == Blocks.WATER)) {
                    final int light = getLight(b, i) >> 4;
                    final boolean[] ao = getAmbientOcclusion(b, i);
                    final BlockFaceContainer face = new BlockFaceContainer(
                            b.getX(), b.getY(), b.getZ(), b.getBlock(), i, light, ao);
                    blockFaceContainers.add(face);
                }
            }
        }

        final int faceCount = blockFaceContainers.size() * 6;
        return GlThreadQueue.getInstance().supply(() -> {
            final ChunkMeshBuilder builder = ChunkMeshBuilder.fixed(faceCount);
            for (BlockFaceContainer b : blockFaceContainers) {
                final int faceId = b.getBlock().getFaces().faceId(b.getDirection());
                builder.addFace(b.getX(), b.getY(), b.getZ(), faceId,
                        b.getDirection(), b.getLight(), b.getAmbientOcclusion());
            }
            return builder;
        });
    }

    private boolean[] getAmbientOcclusion(BlockContainer b, int dir) {
        final Vector3ic direction = ChunkConstants.blockDirections[dir];
        final Vector3ic d1 = ChunkConstants.blockDirections[(dir / 2 * 2 + 2) % 6];
        final Vector3ic d2 = ChunkConstants.blockDirections[(dir / 2 * 2 + 3) % 6];
        final Vector3ic d3 = ChunkConstants.blockDirections[(dir / 2 * 2 + 4) % 6];
        final Vector3ic d4 = ChunkConstants.blockDirections[(dir / 2 * 2 + 5) % 6];
        final int x = getPosition().x() * 16 + b.getX() + direction.x();
        final int y = b.getY() + direction.y();
        final int z = getPosition().y() * 16 + b.getZ() + direction.z();
        final Block[] blocks = {
                this.world.getBlock(x + d1.x(), y + d1.y(), z + d1.z()),
                this.world.getBlock(x + d2.x(), y + d2.y(), z + d2.z()),
                this.world.getBlock(x + d3.x(), y + d3.y(), z + d3.z()),
                this.world.getBlock(x + d4.x(), y + d4.y(), z + d4.z()),
                this.world.getBlock(x + d1.x() + d3.x(), y + d1.y() + d3.y(), z + d1.z() + d3.z()),
                this.world.getBlock(x + d1.x() + d4.x(), y + d1.y() + d4.y(), z + d1.z() + d4.z()),
                this.world.getBlock(x + d2.x() + d3.x(), y + d2.y() + d3.y(), z + d2.z() + d3.z()),
                this.world.getBlock(x + d2.x() + d4.x(), y + d2.y() + d4.y(), z + d2.z() + d4.z()),
        };
        final int[] order = ChunkConstants.ambientOcclusionOrders[dir];

        return new boolean[]{
                blocks[order[0]].isSolid() || blocks[order[1]].isSolid() || blocks[order[2]].isSolid(),
                blocks[order[3]].isSolid() || blocks[order[4]].isSolid() || blocks[order[5]].isSolid(),
                blocks[order[6]].isSolid() || blocks[order[7]].isSolid() || blocks[order[8]].isSolid(),
                blocks[order[9]].isSolid() || blocks[order[10]].isSolid() || blocks[order[11]].isSolid(),
        };
    }

    private int getLight(BlockContainer b, int dir) {
        final Vector3ic direction = ChunkConstants.blockDirections[dir];
        final int x = b.getX() + direction.x();
        final int y = b.getY() + direction.y();
        final int z = b.getZ() + direction.z();
        return getLight(x, y, z);
    }
}
