package org.tendiwa.frontend.gdx2d.plugin.roguelike.actions

import org.tendiwa.backend.modules.roguelike.aspects.inventory
import org.tendiwa.backend.modules.roguelike.playerVolition.drop
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.frontend.gdx2d.TendiwaGame

class DropFirstItemInInventoryAction(
    private val game: TendiwaGame
) : () -> Unit {
    override fun invoke() {
        val playerCharacter = game.playerVolition.host
        val item = playerCharacter.inventory.items.firstOrNull()
        if (item != null) {
            game.playerVolition.drop(item, playerCharacter.position.voxel)
        }
    }
}
