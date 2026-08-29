package org.saar.minecraft

import org.saar.maths.transform.Position
import org.saar.maths.transform.ReadonlyPosition
import org.saar.minecraft.chunk.ChunkConstants
import org.saar.minecraft.generator.WorldGenerator
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.function.Function
import kotlin.math.abs
import kotlin.math.floor

class World(private val generator: WorldGenerator, threads: Int) {
    private val executorService: ExecutorService

    val chunks: MutableList<Chunk> = ArrayList<Chunk>()

    init {
        this.executorService = Executors.newFixedThreadPool(threads)
    }

    fun addChunk(chunk: Chunk) {
        this.chunks.add(chunk)
    }

    fun hasChunk(x: Int, z: Int): Boolean {
        return getChunk(x, z) !== EmptyChunk
    }

    fun getChunk(x: Int, z: Int): IChunk {
        for (chunk in this.chunks) {
            if (chunk.getPosition().equals(x, z)) {
                return chunk
            }
        }
        return EmptyChunk
    }

    fun getChunk(position: Position): IChunk {
        val x: Int = worldToChunkCoordinate(position.x.toInt())
        val z: Int = worldToChunkCoordinate(position.z.toInt())
        return getChunk(x, z)
    }

    fun getBlockContainer(position: Position): BlockContainer {
        return getBlockContainer(position.x, position.y, position.z)
    }

    fun getBlockContainer(wx: Float, wy: Float, wz: Float): BlockContainer {
        val x = floor(wx.toDouble()).toInt()
        val y = floor(wy.toDouble()).toInt()
        val z = floor(wz.toDouble()).toInt()
        return BlockContainer(x, y, z, getBlock(x, y, z))
    }

    fun setLight(x: Int, y: Int, z: Int, value: Byte) {
        val cx: Int = worldToChunkCoordinate(x)
        val cz: Int = worldToChunkCoordinate(z)
        val chunk = getChunk(cx, cz)
        if (chunk is Chunk) {
            val lx = x - cx * ChunkConstants.SIZE
            val lz = z - cz * ChunkConstants.SIZE
            chunk.setLight(lx, y, lz, value)
        }
    }

    fun getLight(x: Int, y: Int, z: Int): Int {
        val cx: Int = worldToChunkCoordinate(x)
        val cz: Int = worldToChunkCoordinate(z)
        val chunk = getChunk(cx, cz)

        val lx = x - cx * ChunkConstants.SIZE
        val lz = z - cz * ChunkConstants.SIZE
        return chunk.getLight(lx, y, lz)
    }

    fun getBlock(position: ReadonlyPosition): Block {
        val x = floor(position.getValue().x().toDouble()).toInt()
        val y = floor(position.getValue().y().toDouble()).toInt()
        val z = floor(position.getValue().z().toDouble()).toInt()
        return getBlock(x, y, z)
    }

    fun getBlock(x: Int, y: Int, z: Int): Block {
        val cx: Int = worldToChunkCoordinate(x)
        val cz: Int = worldToChunkCoordinate(z)
        val chunk = getChunk(cx, cz)

        val lx = x - cx * ChunkConstants.SIZE
        val lz = z - cz * ChunkConstants.SIZE
        return chunk.getBlock(lx, y, lz)
    }

    fun getHeight(x: Int, z: Int): Int {
        val cx: Int = worldToChunkCoordinate(x)
        val cz: Int = worldToChunkCoordinate(z)
        val chunk = getChunk(cx, cz)

        val lx = x - cx * ChunkConstants.SIZE
        val lz = z - cz * ChunkConstants.SIZE
        return chunk.getHeight(lx, lz)
    }

    fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        val cx: Int = worldToChunkCoordinate(x)
        val cz: Int = worldToChunkCoordinate(z)
        val chunk = getChunk(cx, cz)

        val lx = x - cx * ChunkConstants.SIZE
        val lz = z - cz * ChunkConstants.SIZE
        chunk.setBlock(lx, y, lz, block)

        this.executorService.submit(Runnable {
            chunk.updateMesh()
            getChunk(cx - 1, cz).updateMesh()
            getChunk(cx + 1, cz).updateMesh()
            getChunk(cx, cz - 1).updateMesh()
            getChunk(cx, cz + 1).updateMesh()
        })
    }

    fun delete() {
        for (chunk in this.chunks) {
            chunk.delete()
        }
        this.executorService.shutdown()
    }

    fun generateAround(position: Position, radius: Int): CompletableFuture<Void> {
        val px = position.x.toInt()
        val pz = position.z.toInt()
        val cx: Int = worldToChunkCoordinate(px)
        val cz: Int = worldToChunkCoordinate(pz)

        val toDelete: MutableList<Chunk> = ArrayList<Chunk>()
        for (chunk in this.chunks) {
            if (abs(chunk.getPosition().x() - cx) > radius + 1
                || abs(chunk.getPosition().y() - cz) > radius + 1
            ) {
                toDelete.add(chunk)
            }
        }
        toDelete.forEach { obj -> obj.delete() }
        this.chunks.removeAll(toDelete)

        val chunks: MutableList<Chunk> = ArrayList<Chunk>()

        for (x in cx - radius..<cx + radius) {
            for (z in cz - radius..<cz + radius) {
                if (!hasChunk(x, z)) chunks.add(Chunk(this, x, z))
            }
        }

        chunks.sortBy { c -> c.getPosition().distanceSquared(cx, cz).toInt() }

        this.chunks.addAll(chunks)

        val futures = chunks.map { chunk ->
            CompletableFuture
                .runAsync(Runnable { chunk.generate(this.generator) }, this.executorService)
                .exceptionally(Function { f: Throwable ->
                    System.err.println(
                        "Failed to generate chunk " +
                                chunk.getPosition().x() + ", " +
                                chunk.getPosition().y() + " "
                    )
                    f.printStackTrace()
                    null
                })
        }

        return CompletableFuture.allOf(*futures.toTypedArray()).thenRun(
            Runnable {
                chunks.flatMap { c ->
                    listOf(
                        c as IChunk,
                        getChunk(c.getPosition().x(), c.getPosition().y() + 1),
                        getChunk(c.getPosition().x(), c.getPosition().y() - 1),
                        getChunk(c.getPosition().x() + 1, c.getPosition().y()),
                        getChunk(c.getPosition().x() - 1, c.getPosition().y())
                    )
                }.distinct().forEach { obj -> obj.updateMesh() }
            })
    }

    companion object {
        fun worldToChunkCoordinate(w: Int): Int {
            return if (w >= 0) w / ChunkConstants.SIZE else (w + 1) / ChunkConstants.SIZE - 1
        }
    }
}
