package org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.movement

import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.input.UiAction

class MoveEastAction(
    game: TendiwaGame
) : UiAction, MovePlayerCharacterAction(game) {
    override val localizationId: String =
        "org.tendiwa.roguelike.actions.moveEast"

    override fun invoke() {
        move(1, 0, 0)
    }
}
