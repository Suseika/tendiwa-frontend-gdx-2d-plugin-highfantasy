package org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions

import org.tendiwa.backend.space.stimuli.RemovalFromSpace
import org.tendiwa.frontend.gdx2d.GameReaction
import org.tendiwa.frontend.gdx2d.TendiwaGame

class RealThingRemovalFromSpaceReaction(
    game: TendiwaGame
) : GameReaction<RemovalFromSpace>(game) {
    override fun invoke(stimulus: RemovalFromSpace, done: () -> Unit) {
        game.gridActorRegistry.removeActor(stimulus.thing)
        done()
    }
}
