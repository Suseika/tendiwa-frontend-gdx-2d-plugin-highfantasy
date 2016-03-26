package org.tendiwa.frontend.gdx2d.plugin.roguelike.actions

import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.playerVolition.pickUp
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.chunks.chunkWithTile
import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.plane.grid.tiles.move

/**
 * UI action to pick up an item that is at certain displacement from the tile
 * the player is currently at.
 */
class PickUpAction(
    private val game: TendiwaGame,
    private val playerCharacter: RealThing
) : (Int, Int) -> Unit {
    override fun invoke(dx: Int, dy: Int) {
        val pickUpTile = playerCharacter.aspect<Position>().tile.move(dx, dy)
        val item = game.reality.space.realThings
            .planeAtZ(playerCharacter.aspect<Position>().voxel.z)
            .chunkWithTile(pickUpTile)
            .things
            .filter { it.aspect<Position>().tile == pickUpTile && it is Item }
            .firstOrNull() as Item?
        if (item != null) {
            game.playerVolition.pickUp(item)
        }
    }
}
