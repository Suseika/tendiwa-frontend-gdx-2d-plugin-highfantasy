package org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions

import org.tendiwa.backend.space.stimuli.RemovalFromSpace
import org.tendiwa.backend.space.tile
import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.GameReaction

class RealThingRemovalFromSpaceReaction(
    game: TendiwaGame
) : GameReaction<RemovalFromSpace>(game) {
    override fun invoke(stimulus: RemovalFromSpace, done: () -> Unit) {
        game.gridActorRegistry.removeActor(stimulus.voxel.tile, stimulus.thing)
        done()
    }
}
