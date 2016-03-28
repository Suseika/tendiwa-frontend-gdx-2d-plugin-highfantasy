package org.tendiwa.frontend.gdx2d.plugin.roguelike.actions

import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.input.TileAction

class PrintTileUnderCursor(val game: TendiwaGame) : TileAction {
    override val localizationId: String =
        "org.tendiwa.unnamed"

    override fun invoke(x: Int, y: Int) {
        println(
            game.camera.screenCoordinatesToTileCoordinates(x, y)
        )
    }
}
