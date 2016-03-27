package org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.movement

import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.input.UiAction

class MoveWestAction(
    game: TendiwaGame
) : UiAction, MovePlayerCharacterAction(game) {
    override val localizationId = "org.tendiwa.moveWest"

    override fun invoke() {
        move(-1, 0, 0)
    }

}
