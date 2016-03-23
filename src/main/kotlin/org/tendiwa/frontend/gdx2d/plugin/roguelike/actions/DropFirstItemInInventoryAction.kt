package org.tendiwa.frontend.gdx2d.plugin.roguelike.actions

import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.modules.roguelike.aspects.Inventory
import org.tendiwa.backend.modules.roguelike.playerVolition.drop
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.frontend.gdx2d.TendiwaGame

class DropFirstItemInInventoryAction(
    private val game: TendiwaGame
) : () -> Unit {
    override fun invoke() {
        val playerCharacter = game.playerVolition.host
        val item = playerCharacter.aspect<Inventory>().items.firstOrNull()
        if (item != null) {
            game.playerVolition.drop(
                item,
                playerCharacter.aspect<Position>().voxel
            )
        }
    }
}
