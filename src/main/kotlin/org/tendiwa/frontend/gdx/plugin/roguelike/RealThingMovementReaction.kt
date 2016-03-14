package org.tendiwa.frontend.gdx.plugin.roguelike

import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.client.gdx.TendiwaGame

class RealThingMovementReaction(
    game: TendiwaGame
) : GameReaction<Position.Change>(game) {

    override fun invoke(stimulus: Position.Change) {
        game.gridActorRegistry
            .actorOf(stimulus.host)
            .addAction(
                MoveToAction().apply {
                    x = stimulus.to.x.toFloat()
                    y = stimulus.to.y.toFloat()
                    duration = 0.1f
                }
            )
    }

}
