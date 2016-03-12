package org.tendiwa.frontend.gdx.plugin.roguelike

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import org.tendiwa.backend.modules.roguelike.aspects.playerVision
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.client.gdx.TendiwaGame
import org.tendiwa.client.gdx.TendiwaGdxClientPlugin
import org.tendiwa.frontend.generic.move
import org.tendiwa.plane.grid.rectangles.GridRectangle

class RoguelikePlugin : TendiwaGdxClientPlugin {
    override fun init(game: TendiwaGame) {
        game.apply {
            val playerCharacter = reality.hostOf(playerVolition)
            fun movePlayerCharacter(dx: Int, dy: Int): Boolean {
                if (dx != 0 || dy != 0) {
                    camera.position.add(dx.toFloat(), dy.toFloat(), 0f)
                    vicinity.tileBounds = vicinity.tileBounds.let {
                        GridRectangle(
                            it.x + dx,
                            it.y + dy,
                            it.width,
                            it.height
                        )
                    }
                    val currentTile = playerCharacter.position.tile
                    playerVolition.move(
                        reality,
                        currentTile.x + dx,
                        currentTile.y + dy
                    )
                    return true
                }
                return false
            }
            frontendStimulusMedium.apply {
                registerReaction(
                    Position.Change::class.java,
                    { stimulus ->
                        realThingActorRegistry.actorOf(stimulus.host).addAction(
                            MoveToAction().apply {
                                x = stimulus.to.x.toFloat()
                                y = stimulus.to.y.toFloat()
                                duration = 0.1f
                            }
                        )
                    }
                )
                registerReaction(
                    Position.Change::class.java,
                    { stimulus ->
                        vicinity.fieldOfView =
                            playerCharacter.playerVision.fieldOfView.mask
                    }
                )
            }
            vicinity.fieldOfView = playerCharacter.playerVision.fieldOfView.mask
            keysSetup.addAction(Input.Keys.LEFT, { movePlayerCharacter(-1, 0) })
            keysSetup.addAction(Input.Keys.RIGHT, { movePlayerCharacter(1, 0) })
            keysSetup.addAction(Input.Keys.UP, { movePlayerCharacter(0, 1) })
            keysSetup.addAction(Input.Keys.DOWN, { movePlayerCharacter(0, -1) })
        }
    }
}

