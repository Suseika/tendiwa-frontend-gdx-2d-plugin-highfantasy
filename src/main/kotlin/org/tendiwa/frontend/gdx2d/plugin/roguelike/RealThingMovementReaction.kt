package org.tendiwa.frontend.gdx2d.plugin.roguelike

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.frontend.gdx2d.TendiwaGame

class RealThingMovementReaction(
    game: TendiwaGame
) : GameReaction<Position.Change>(game) {

    override fun invoke(stimulus: Position.Change, done: () -> Unit) {
        game.gridActorRegistry
            .actorOf(stimulus.host)
            .addAction(
                Actions.sequence(
                    MoveToAction().apply {
                        x = stimulus.to.x.toFloat()
                        y = stimulus.to.y.toFloat()
                        duration = 0.1f
                    },
                    Actions.run { done() }
                )
            )
    }

}
