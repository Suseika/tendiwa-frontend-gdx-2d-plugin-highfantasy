package org.tendiwa.frontend.gdx2d.plugin.roguelike

import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.playerVolition.pickUp
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.backend.space.chunks.chunkWithTile
import org.tendiwa.backend.space.realThing.realThings
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
        val pickUpTile = playerCharacter.position.tile.move(dx, dy)
        val thingsToPickUp = game.reality.space.realThings
            .chunkWithTile(pickUpTile)
            .things
            .filter { it.position.tile == pickUpTile && it is Item }
        if (thingsToPickUp.size == 1) {
            val item = thingsToPickUp.single() as Item
            game.playerVolition.pickUp(item)
        }
    }
}
