package org.tendiwa.frontend.gdx2d.plugin.roguelike.setup

import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys.*
import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.input.UiMode
import org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.DropFirstItemInInventoryAction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.PickUpAction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.PrintTileUnderCursor
import org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.movement.*

fun setupKeybindings(game: TendiwaGame) {
    game.keyCombinationPool.apply {
        game.inputProcessor.uiMode = UiMode(
            combination(LEFT) to MoveWestAction(game),
            combination(RIGHT) to MoveEastAction(game),
            combination(DOWN) to MoveSouthAction(game),
            combination(UP) to MoveNorthAction(game),
            combination(COMMA, shift = true) to MoveUpAction(game),
            combination(PERIOD, shift = true) to MoveDownAction(game),
            combination(G) to PickUpAction(game),
            combination(D) to DropFirstItemInInventoryAction(game)
        )
        game.inputProcessor.uiMode?.set(
            Input.Buttons.LEFT,
            PrintTileUnderCursor(game)
        )
    }
}
