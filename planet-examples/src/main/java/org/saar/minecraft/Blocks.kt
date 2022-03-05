package org.saar.minecraft

object Blocks {

    @JvmField
    val AIR: Block = object : Block {
        override val id: Int = 0
        override val name: String = "air"
        override val isSolid: Boolean = false
        override val isTransparent: Boolean = true
        override val isCollideable: Boolean = false
        override val lightPropagation = LightPropagation(0x10, 0x00)
        override val faces: BlockFaces = BlockFaces(0)
    }

    @JvmField
    val STONE: Block = object : Block {
        override val id: Int = 1
        override val name: String = "stone"
        override val isSolid: Boolean = true
        override val isTransparent: Boolean = false
        override val isCollideable: Boolean = true
        override val faces: BlockFaces = BlockFaces(1)
    }

    @JvmField
    val GRASS: Block = object : Block {
        override val id: Int = 2
        override val name: String = "grass"
        override val isSolid: Boolean = true
        override val isTransparent: Boolean = false
        override val isCollideable: Boolean = true
        override val faces: BlockFaces = BlockFaces(2, 3, 4)
    }

    @JvmField
    val DIRT: Block = object : Block {
        override val id: Int = 3
        override val name: String = "dirt"
        override val isSolid: Boolean = true
        override val isTransparent: Boolean = false
        override val isCollideable: Boolean = true
        override val faces: BlockFaces = BlockFaces(4)
    }

    @JvmField
    val TREE: Block = object : Block {
        override val id: Int = 4
        override val name: String = "tree"
        override val isSolid: Boolean = true
        override val isTransparent: Boolean = false
        override val isCollideable: Boolean = true
        override val faces: BlockFaces = BlockFaces(5, 6)
    }

    @JvmField
    val LEAVES: Block = object : Block {
        override val id: Int = 5
        override val name: String = "leave"
        override val isSolid: Boolean = true
        override val isTransparent: Boolean = true
        override val isCollideable: Boolean = true
        override val faces: BlockFaces = BlockFaces(7)
    }

    @JvmField
    val BEDROCK: Block = object : Block {
        override val id: Int = 6
        override val name: String = "bedrock"
        override val isSolid: Boolean = true
        override val isTransparent: Boolean = false
        override val isCollideable: Boolean = true
        override val faces: BlockFaces = BlockFaces(8)
    }

    @JvmField
    val WATER: Block = object : Block {
        override val id: Int = 7
        override val name: String = "bedrock"
        override val isSolid: Boolean = false
        override val isTransparent: Boolean = true
        override val isCollideable: Boolean = false
        override val lightPropagation = LightPropagation(0x0A, 0x0A)
        override val faces: BlockFaces = BlockFaces(9, 13)
    }

    @JvmField
    val SAND: Block = object : Block {
        override val id: Int = 8
        override val name: String = "water"
        override val isSolid: Boolean = true
        override val isTransparent: Boolean = false
        override val isCollideable: Boolean = true
        override val faces: BlockFaces = BlockFaces(14)
    }

    val ALL: List<Block> = this.javaClass.declaredFields
        .filter { it.type == Block::class.java }
        .map { it.get(this) as Block }
}