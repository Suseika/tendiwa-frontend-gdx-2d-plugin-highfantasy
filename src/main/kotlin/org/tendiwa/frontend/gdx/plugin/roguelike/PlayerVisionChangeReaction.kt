package org.tendiwa.frontend.gdx.plugin.roguelike

import org.tendiwa.backend.modules.roguelike.aspects.PlayerVision
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.backend.space.realThing.realThings
import org.tendiwa.backend.space.realThing.viewOfArea
import org.tendiwa.backend.space.walls.hasWallAt
import org.tendiwa.backend.space.walls.walls
import org.tendiwa.client.gdx.TendiwaGame

internal class PlayerVisionChangeReaction(
    game: TendiwaGame
) : GameReaction<PlayerVision.Change>(game) {

    override fun invoke(stimulus: PlayerVision.Change) {
        game.apply {
            vicinity.updateFieldOfView(stimulus.new)
            stimulus.old
                .difference(stimulus.new)
                .let { difference ->
                    gridActorRegistry.apply {
                        difference.seen
                            .filter { reality.space.walls.hasWallAt(it) }
                            .forEach { spawnWall(it) }
                        reality.space.realThings
                            .viewOfArea(vicinity.tileBounds)
                            .things
                            .filter {
                                val tile = it.position.tile
                                difference.seen.any { it == tile }
                            }
                            .forEach { spawnRealThing(it) }
                        difference.unseen
                            .filter { reality.space.walls.hasWallAt(it) }
                            .forEach { removeWallActor(it) }
                        reality.space.realThings
                            .viewOfArea(vicinity.tileBounds)
                            .things
                            .filter {
                                val tile = it.position.tile
                                difference.unseen.any { it == tile }
                            }
                            .forEach { removeActor(it.position.tile, it) }
                    }
                }
        }
    }

}
