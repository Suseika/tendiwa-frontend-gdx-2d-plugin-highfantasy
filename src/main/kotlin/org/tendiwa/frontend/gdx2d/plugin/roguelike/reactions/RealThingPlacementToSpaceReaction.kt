package org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions

import org.tendiwa.backend.space.stimuli.PlacementToSpace
import org.tendiwa.frontend.gdx2d.GameReaction
import org.tendiwa.frontend.gdx2d.TendiwaGame

class RealThingPlacementToSpaceReaction(
    game: TendiwaGame
) : GameReaction<PlacementToSpace>(game) {
    override fun invoke(stimulus: PlacementToSpace, done: () -> Unit) {
        game.gridActorRegistry.spawnRealThing(stimulus.thing)
        done()
    }
}
