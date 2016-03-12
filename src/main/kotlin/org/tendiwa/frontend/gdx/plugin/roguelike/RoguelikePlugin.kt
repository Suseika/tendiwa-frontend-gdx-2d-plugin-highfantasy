package org.tendiwa.frontend.gdx.plugin.roguelike

import com.badlogic.gdx.Input
import org.tendiwa.backend.modules.roguelike.aspects.playerVision
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.client.gdx.TendiwaCamera
import org.tendiwa.client.gdx.TendiwaGdxClientPlugin
import org.tendiwa.client.gdx.input.KeysSetup
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.frontend.generic.move
import org.tendiwa.plane.grid.rectangles.GridRectangle

class RoguelikePlugin : TendiwaGdxClientPlugin {
    override fun init(
        camera: TendiwaCamera,
        vicinity: RenderingVicinity,
        playerVolition: PlayerVolition,
        keysSetup: KeysSetup,
        reality: Reality
    ) {
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
        vicinity.fieldOfView = playerCharacter.playerVision.fieldOfView.mask
        keysSetup.addAction(Input.Keys.LEFT, { movePlayerCharacter(-1, 0) })
        keysSetup.addAction(Input.Keys.RIGHT, { movePlayerCharacter(1, 0) })
        keysSetup.addAction(Input.Keys.UP, { movePlayerCharacter(0, 1) })
        keysSetup.addAction(Input.Keys.DOWN, { movePlayerCharacter(0, -1) })
    }
}

