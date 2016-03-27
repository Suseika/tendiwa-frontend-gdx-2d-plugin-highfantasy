package org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.movement

import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.input.UiAction

class MoveUpAction(
    game: TendiwaGame
) : UiAction, MovePlayerCharacterAction(game) {
    override val localizationId: String =
        "org.tendiwa.roguelike.actions.moveUp"

    override fun invoke() {
        move(0, 0, 1)
    }
}
