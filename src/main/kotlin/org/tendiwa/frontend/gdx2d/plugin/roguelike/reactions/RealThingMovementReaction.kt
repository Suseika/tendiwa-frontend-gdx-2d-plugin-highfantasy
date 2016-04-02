package org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.frontend.gdx2d.GameReaction
import org.tendiwa.frontend.gdx2d.TendiwaGame

class RealThingMovementReaction(
    game: TendiwaGame
) : GameReaction<Position.Change>(game) {

    override fun invoke(stimulus: Position.Change, done: () -> Unit) {
        game.gridActorRegistry.apply {
            val comesIntoVision =
                actorOf(stimulus.host) == null && tileIsVisible(stimulus.new)
            val comesOutOfVision =
                actorOf(stimulus.host) != null && !tileIsVisible(stimulus.new)
            if (comesIntoVision) {
                spawnRealThing(stimulus.host)
                actorOf(stimulus.host)!!.setPosition(
                    stimulus.old.x.toFloat(),
                    stimulus.old.y.toFloat()
                )
            }
            actorOf(stimulus.host)
                ?.addAction(
                    Actions.sequence(
                        MoveToAction().apply {
                            x = stimulus.new.x.toFloat()
                            y = stimulus.new.y.toFloat()
                            duration = 0.1f
                        },
                        Actions.run {
                            if (comesOutOfVision) {
                                removeActor(stimulus.host)
                            }
                            done()
                        }
                    )
                )
                ?: done()
        }
    }

    private fun tileIsVisible(voxel: Voxel) =
        game.vicinity
            .fieldOfView
            .contains(voxel.x, voxel.y)

}
