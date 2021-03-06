package gg.rsmod.plugins.api.ext

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World

/**
 * @author Tom <rspsmods@gmail.com>
 */

fun Tile.isMulti(world: World): Boolean {
    val region = regionId
    val chunk = chunkCoords.hashCode()
    return world.multiCombatChunks.contains(chunk) || world.multiCombatRegions.contains(region)
}

fun Tile.getWildernessLevel(): Int {

    if (x !in 2941..3392 || z !in 3524..3968) {
        return 0
    }

    val z = if (this.z > 6400) this.z - 6400 else this.z
    return (((z - 3525) shr 3) + 1)
}