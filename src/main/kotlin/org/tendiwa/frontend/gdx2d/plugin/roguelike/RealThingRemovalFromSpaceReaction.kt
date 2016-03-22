package org.tendiwa.frontend.gdx2d.plugin.roguelike

import org.tendiwa.backend.space.stimuli.RemovalFromSpace
import org.tendiwa.backend.space.tile
import org.tendiwa.frontend.gdx2d.TendiwaGame

class RealThingRemovalFromSpaceReaction(
    game: TendiwaGame
) : GameReaction<RemovalFromSpace>(game) {
    override fun invoke(stimulus: RemovalFromSpace, done: () -> Unit) {
        game.gridActorRegistry.removeActor(stimulus.voxel.tile, stimulus.thing)
        done()
    }
}
