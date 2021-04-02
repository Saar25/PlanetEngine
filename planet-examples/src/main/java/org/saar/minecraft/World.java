package org.saar.minecraft;

import org.saar.maths.transform.Position;
import org.saar.minecraft.generator.WorldGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class World {

    private final ExecutorService executorService;

    private final List<Chunk> chunks = new ArrayList<>();
    private final WorldGenerator generator;

    public World(WorldGenerator generator, int threads) {
        this.generator = generator;
        this.executorService = Executors.newFixedThreadPool(threads);
    }

    private static int worldToChunkCoordinate(int w) {
        return w >= 0 ? w / 16 : (w + 1) / 16 - 1;
    }

    public void addChunk(Chunk chunk) {
        getChunks().add(chunk);
    }

    public boolean hasChunk(int x, int z) {
        return getChunk(x, z) != EmptyChunk.getInstance();
    }

    public IChunk getChunk(int x, int z) {
        for (Chunk chunk : getChunks()) {
            if (chunk.getPosition().equals(x, z)) {
                return chunk;
            }
        }
        return EmptyChunk.getInstance();
    }

    public IChunk getChunk(Position position) {
        final int x = worldToChunkCoordinate((int) position.getX());
        final int z = worldToChunkCoordinate((int) position.getZ());
        return getChunk(x, z);
    }

    public BlockContainer getBlockContainer(Position position) {
        return getBlockContainer(position.getX(), position.getY(), position.getZ());
    }

    public BlockContainer getBlockContainer(float wx, float wy, float wz) {
        final int x = (int) Math.floor(wx);
        final int y = (int) Math.floor(wy);
        final int z = (int) Math.floor(wz);
        return new BlockContainer(x, y, z, getBlock(x, y, z));
    }

    public Block getBlock(Position position) {
        final int x = (int) Math.floor(position.getX());
        final int y = (int) Math.floor(position.getY());
        final int z = (int) Math.floor(position.getZ());
        return getBlock(x, y, z);
    }

    public Block getBlock(int x, int y, int z) {
        final int cx = worldToChunkCoordinate(x);
        final int cz = worldToChunkCoordinate(z);
        final IChunk chunk = getChunk(cx, cz);

        final int lx = x - cx * 16;
        final int lz = z - cz * 16;
        return chunk.getBlock(lx, y, lz);
    }

    public void setBlock(int x, int y, int z, Block block) {
        final int cx = worldToChunkCoordinate(x);
        final int cz = worldToChunkCoordinate(z);
        final IChunk chunk = getChunk(cx, cz);

        final int lx = x - cx * 16;
        final int lz = z - cz * 16;
        chunk.setBlock(lx, y, lz, block);

        this.executorService.submit(() -> {
            chunk.updateMesh(this);
            if (lx == 0x0) getChunk(cx - 1, cz).updateMesh(this);
            if (lx == 0xF) getChunk(cx + 1, cz).updateMesh(this);
            if (lz == 0x0) getChunk(cx, cz - 1).updateMesh(this);
            if (lz == 0xF) getChunk(cx, cz + 1).updateMesh(this);
        });
    }

    public List<Chunk> getChunks() {
        return this.chunks;
    }

    public void delete() {
        for (Chunk chunk : getChunks()) {
            chunk.delete();
        }
        this.executorService.shutdown();
    }

    public void generateAround(Position position, int radius) {
        final int px = (int) position.getX();
        final int pz = (int) position.getZ();
        final int cx = worldToChunkCoordinate(px);
        final int cz = worldToChunkCoordinate(pz);

        final List<Chunk> toDelete = new ArrayList<>();
        for (Chunk chunk : getChunks()) {
            if (Math.abs(chunk.getPosition().x() - cx) > radius + 1
                    || Math.abs(chunk.getPosition().y() - cz) > radius + 1) {
                toDelete.add(chunk);
            }
        }
        toDelete.forEach(Chunk::delete);
        getChunks().removeAll(toDelete);

        final List<Chunk> chunks = new ArrayList<>();

        for (int x = cx - radius; x < cx + radius; x++) {
            for (int z = cz - radius; z < cz + radius; z++) {
                if (!hasChunk(x, z)) chunks.add(new Chunk(x, z));
            }
        }

        chunks.sort(Comparator.comparingInt(c -> (int) c.getPosition().distanceSquared(cx, cz)));

        final List<CompletableFuture<Void>> futures = new ArrayList<>(chunks.size());

        for (Chunk chunk : chunks) {
            addChunk(chunk);

            futures.add(CompletableFuture.runAsync(
                    () -> this.generator.generateChunk(chunk),
                    this.executorService));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() ->
                chunks.stream().flatMap(c -> Stream.of(c,
                        getChunk(c.getPosition().x(), c.getPosition().y() + 1),
                        getChunk(c.getPosition().x(), c.getPosition().y() - 1),
                        getChunk(c.getPosition().x() + 1, c.getPosition().y()),
                        getChunk(c.getPosition().x() - 1, c.getPosition().y()))
                ).distinct().forEach(c -> c.updateMesh(this)));
    }
}
