package org.tendiwa.frontend.gdx2d.plugin.roguelike

import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.centerOnTile
import org.tendiwa.frontend.generic.move
import org.tendiwa.plane.grid.masks.contains
import org.tendiwa.plane.grid.tiles.move

class MovePlayerCharacterAction(
    private val game: TendiwaGame,
    private val playerCharacter: RealThing
) : (Int, Int) -> Boolean {
    override fun invoke(dx: Int, dy: Int): Boolean {
        if (dx == 0 && dy == 0) {
            return false
        }
        val currentTile = playerCharacter.position.tile
        val targetTile = currentTile.move(dx, dy)
        if (!game.reality.space.hull.contains(targetTile)) {
            return false
        }
        game.camera.centerOnTile(targetTile)
        game.playerVolition.move(
            targetTile.x,
            targetTile.y
        )
        return true
    }
}