package org.tendiwa.frontend.gdx.plugin.roguelike

import com.badlogic.gdx.Input
import org.tendiwa.client.gdx.TendiwaCamera
import org.tendiwa.client.gdx.TendiwaGdxClientPlugin
import org.tendiwa.client.gdx.input.KeysSetup
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.plane.grid.rectangles.GridRectangle

class RoguelikePlugin : TendiwaGdxClientPlugin {
    override fun init(
        camera: TendiwaCamera,
        vicinity: RenderingVicinity,
        playerVolition: PlayerVolition,
        KeysSetup: KeysSetup
    ) {
        fun moveCamera(dx: Int, dy: Int): Boolean {
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
                return true
            }
            return false
        }
        KeysSetup.addAction(Input.Keys.LEFT, { moveCamera(-1, 0) })
        KeysSetup.addAction(Input.Keys.RIGHT, { moveCamera(1, 0) })
        KeysSetup.addAction(Input.Keys.UP, { moveCamera(0, 1) })
        KeysSetup.addAction(Input.Keys.DOWN, { moveCamera(0, -1) })
    }

}
