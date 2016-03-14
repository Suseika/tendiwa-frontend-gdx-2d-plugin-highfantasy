package org.tendiwa.frontend.gdx.plugin.roguelike

import com.badlogic.gdx.Input
import org.tendiwa.backend.modules.roguelike.aspects.PlayerVision
import org.tendiwa.backend.modules.roguelike.aspects.playerVision
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.client.gdx.TendiwaGame
import org.tendiwa.client.gdx.TendiwaGdxClientPlugin
import org.tendiwa.client.gdx.centerOnTile
import org.tendiwa.frontend.generic.RenderingVicinity

class RoguelikePlugin : TendiwaGdxClientPlugin {
    override fun init(game: TendiwaGame) {
        game.apply {
            val playerCharacter = game.reality.hostOf(game.playerVolition)
            val realThingMoves = RealThingMovementReaction(game)
            val fieldOfViewGetsRedrawn = PlayerVisionChangeReaction(game)
            frontendStimulusMedium.apply {
                registerReaction(
                    Position.Change::class.java,
                    realThingMoves
                )
                registerReaction(
                    PlayerVision.Change::class.java,
                    fieldOfViewGetsRedrawn
                )
            }
            vicinity.updateFieldOfView(playerCharacter.playerVision.fieldOfView)
            camera.centerOnTile(playerCharacter.position.tile)
            keysSetup.apply {
                MovePlayerCharacterAction(game, playerCharacter)
                    .let {
                        move ->
                        addAction(Input.Keys.LEFT, { move(-1, 0) })
                        addAction(Input.Keys.RIGHT, { move(1, 0) })
                        addAction(Input.Keys.UP, { move(0, 1) })
                        addAction(Input.Keys.DOWN, { move(0, -1) })
                    }
            }
        }
    }

}

fun RenderingVicinity.updateFieldOfView(fieldOfView: PlayerVision.FieldOfView) {
    this.fieldOfView = fieldOfView.mask
    this.tileBounds = fieldOfView.hull
}

