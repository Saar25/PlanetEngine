package org.saar.minecraft

import org.joml.Vector2i
import org.joml.Vector2ic
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.core.mesh.async.FutureMesh
import org.saar.core.mesh.common.EmptyMesh
import org.saar.maths.transform.Position.Companion.of
import org.saar.minecraft.chunk.*
import org.saar.minecraft.chunk.Chunks.meshBuilder
import org.saar.minecraft.generator.WorldGenerator
import org.saar.minecraft.threading.GlThreadQueue
import java.util.concurrent.CompletableFuture

class Chunk(private val world: World, x: Int, z: Int) : IChunk, Model {
    val position = Vector2i(x, z)

    private val blocks = ChunkBlocks()
    private val heights = ChunkHeights(this)
    private val lights = ChunkLights(this)
    val bounds: ChunkBounds = ChunkBounds()
    private var generating = false

    override var mesh: Mesh = EmptyMesh
    private var waterMesh: Mesh = EmptyMesh
    private var meshUpdateNeeded = false

    fun getPosition(): Vector2ic {
        return this.position
    }

    fun getRelativeChunk(x: Int, z: Int): IChunk {
        val wx = x + getPosition().x() * 16
        val wz = z + getPosition().y() * 16
        return this.world.getChunk(of(wx.toFloat(), 0f, wz.toFloat()))
    }

    override fun getHeight(x: Int, z: Int): Int {
        if (x !in 0..0xF || z < 0 || z > 0xF) {
            val wx = x + getPosition().x() * 16
            val wz = z + getPosition().y() * 16
            return this.world.getHeight(wx, wz)
        }
        return this.heights.getHeight(x, z)
    }

    override fun getLight(x: Int, y: Int, z: Int): Int {
        if (y < 0) return 0
        if (y > 0xFF) return 0xF
        if (x !in 0..0xF || z < 0 || z > 0xF) {
            val wx = x + getPosition().x() * 16
            val wz = z + getPosition().y() * 16
            return this.world.getLight(wx, y, wz)
        }
        return this.lights.getLight(x, y, z)
    }

    fun setLight(x: Int, y: Int, z: Int, value: Byte) {
        if (y < 0 || y > 0xFF) return
        if (x < 0 || x > 0xF || z < 0 || z > 0xF) {
            val wx = x + getPosition().x() * 16
            val wz = z + getPosition().y() * 16
            this.world.setLight(wx, y, wz, value)
        } else {
            this.meshUpdateNeeded = true
            this.lights.setLight(x, y, z, value)
        }
    }

    override fun getBlock(x: Int, y: Int, z: Int): Block {
        if (y < 0 || y > 0xFF) return Blocks.AIR
        if (x < 0 || x > 0xF || z < 0 || z > 0xF) {
            val wx = x + getPosition().x() * 16
            val wz = z + getPosition().y() * 16
            return this.world.getBlock(wx, y, wz)
        }
        return this.blocks.getBlock(x, y, z)
    }

    fun setBlockIfEmpty(x: Int, y: Int, z: Int, block: Block) {
        if (getBlock(x, y, z) === Blocks.AIR) {
            setBlock(x, y, z, block)
        }
    }

    fun generate(generator: WorldGenerator) {
        this.generating = true
        generator.generateChunk(this)
        this.lights.recalculateLight()
        this.generating = false
    }

    override fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        if (y < 0 || y > 0xFF) return
        if (x < 0 || x > 0xF || z < 0 || z > 0xF) {
            val wx = x + getPosition().x() * 16
            val wz = z + getPosition().y() * 16
            this.world.setBlock(wx, y, wz, block)
        } else {
            this.blocks.setBlock(x, y, z, block)

            val wx = x + getPosition().x() * 16
            val wz = z + getPosition().y() * 16
            this.bounds.addBlock(wx, y, wz)

            if (block !== Blocks.AIR) {
                this.heights.addBlock(x, y, z)
            } else {
                this.heights.removeBlock(x, y, z)
            }

            if (!this.generating) {
                this.lights.updateLight(x, y, z)
            }

            this.meshUpdateNeeded = true
            if (x == 0) {
                val chunk = world.getChunk(getPosition().x() - 1, getPosition().y())
                if (chunk is Chunk) chunk.meshUpdateNeeded = true
            } else if (x == 0xF) {
                val chunk = world.getChunk(getPosition().x() + 1, getPosition().y())
                if (chunk is Chunk) chunk.meshUpdateNeeded = true
            }
            if (z == 0) {
                val chunk = world.getChunk(getPosition().x(), getPosition().y() - 1)
                if (chunk is Chunk) chunk.meshUpdateNeeded = true
            } else if (z == 0xF) {
                val chunk = world.getChunk(getPosition().x(), getPosition().y() + 1)
                if (chunk is Chunk) chunk.meshUpdateNeeded = true
            }
        }
    }

    private fun blocksAround(x: Int, y: Int, z: Int): Array<Block> {
        val xPos = getBlock(x + 1, y, z)
        val xNeg = getBlock(x - 1, y, z)
        val yPos = getBlock(x, y + 1, z)
        val yNeg = getBlock(x, y - 1, z)
        val zPos = getBlock(x, y, z + 1)
        val zNeg = getBlock(x, y, z - 1)
        return arrayOf<Block>(xPos, xNeg, yPos, yNeg, zPos, zNeg)
    }

    override fun updateMesh() {
        if (!this.meshUpdateNeeded) return
        this.meshUpdateNeeded = false

        this.mesh.let { GlThreadQueue.supply { it.delete() } }
        this.mesh = FutureMesh.Companion.unloaded(writeMesh(this.blocks.opaque))

        this.waterMesh.let { GlThreadQueue.supply { it.delete() } }
        this.waterMesh = FutureMesh.Companion.unloaded(writeMesh(this.blocks.water))
    }

    override fun draw() {
        drawOpaque()
        drawWater()
    }

    fun drawOpaque() {
        this.mesh.draw()
    }

    fun drawWater() {
        this.waterMesh.draw()
    }

    override fun delete() {
        this.mesh.delete()
        this.waterMesh.delete()
    }

    private fun writeMesh(blocks: MutableList<BlockContainer>): CompletableFuture<ChunkMeshBuilder> {
        val blockFaceContainers: MutableList<BlockFaceContainer> = ArrayList<BlockFaceContainer>()
        for (b in blocks) {
            val around = blocksAround(b.x, b.y, b.z)

            for (i in around.indices) {
                if (around[i].isTransparent && !(around[i] === Blocks.WATER && b.block === Blocks.WATER)) {
                    val light = getLight(b, i) shr 4
                    val ao = getAmbientOcclusion(b, i)
                    val face = BlockFaceContainer(
                        b.x, b.y, b.z, b.block, i, light, ao
                    )
                    blockFaceContainers.add(face)
                }
            }
        }

        return GlThreadQueue.supply {
            val vertices = blockFaceContainers.size * 6
            val builder = meshBuilder(vertices)
            for (b in blockFaceContainers) {
                val faceId = b.block.faces.faceId(b.direction)
                builder.addFace(
                    b.x, b.y, b.z, faceId,
                    b.direction, b.light, b.ambientOcclusion
                )
            }
            builder
        }
    }

    private fun getAmbientOcclusion(b: BlockContainer, dir: Int): BooleanArray {
        val direction = ChunkConstants.blockDirections[dir]
        val d1 = ChunkConstants.blockDirections[(dir / 2 * 2 + 2) % 6]
        val d2 = ChunkConstants.blockDirections[(dir / 2 * 2 + 3) % 6]
        val d3 = ChunkConstants.blockDirections[(dir / 2 * 2 + 4) % 6]
        val d4 = ChunkConstants.blockDirections[(dir / 2 * 2 + 5) % 6]
        val x = getPosition().x() * 16 + b.x + direction.x()
        val y = b.y + direction.y()
        val z = getPosition().y() * 16 + b.z + direction.z()
        val blocks = arrayOf<Block>(
            this.world.getBlock(x + d1.x(), y + d1.y(), z + d1.z()),
            this.world.getBlock(x + d2.x(), y + d2.y(), z + d2.z()),
            this.world.getBlock(x + d3.x(), y + d3.y(), z + d3.z()),
            this.world.getBlock(x + d4.x(), y + d4.y(), z + d4.z()),
            this.world.getBlock(x + d1.x() + d3.x(), y + d1.y() + d3.y(), z + d1.z() + d3.z()),
            this.world.getBlock(x + d1.x() + d4.x(), y + d1.y() + d4.y(), z + d1.z() + d4.z()),
            this.world.getBlock(x + d2.x() + d3.x(), y + d2.y() + d3.y(), z + d2.z() + d3.z()),
            this.world.getBlock(x + d2.x() + d4.x(), y + d2.y() + d4.y(), z + d2.z() + d4.z()),
        )
        val order = ChunkConstants.ambientOcclusionOrders[dir]

        return booleanArrayOf(
            blocks[order[0]].isSolid || blocks[order[1]].isSolid || blocks[order[2]].isSolid,
            blocks[order[3]].isSolid || blocks[order[4]].isSolid || blocks[order[5]].isSolid,
            blocks[order[6]].isSolid || blocks[order[7]].isSolid || blocks[order[8]].isSolid,
            blocks[order[9]].isSolid || blocks[order[10]].isSolid || blocks[order[11]].isSolid,
        )
    }

    private fun getLight(b: BlockContainer, dir: Int): Int {
        val direction = ChunkConstants.blockDirections[dir]
        val x = b.x + direction.x()
        val y = b.y + direction.y()
        val z = b.z + direction.z()
        return getLight(x, y, z)
    }
}
