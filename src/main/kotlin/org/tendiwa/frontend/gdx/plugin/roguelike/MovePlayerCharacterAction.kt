package org.tendiwa.frontend.gdx.plugin.roguelike

import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.client.gdx.TendiwaGame
import org.tendiwa.client.gdx.centerOnTile
import org.tendiwa.frontend.generic.move
import org.tendiwa.plane.grid.masks.contains
import org.tendiwa.plane.grid.tiles.move

class MovePlayerCharacterAction(
    private val game: TendiwaGame,
    private val playerCharacter: RealThing
) : (Int, Int) -> Boolean {
    override fun invoke(dx: Int, dy: Int): Boolean {
        game.apply {
            if (dx != 0 || dy != 0) {
                val currentTile = playerCharacter.position.tile
                val targetTile = currentTile.move(dx, dy)
                if (!game.reality.space.hull.contains(targetTile)) {
                    return false
                }
                game.camera.centerOnTile(targetTile)
                playerVolition.move(
                    reality,
                    targetTile.x,
                    targetTile.y
                )
                return true
            }
        }
        return false
    }
}
