package org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions

import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.modules.roguelike.aspects.PlayerVision
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.realThing.viewOfArea
import org.tendiwa.backend.space.walls.hasWallAt
import org.tendiwa.frontend.gdx2d.GameReaction
import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.plugin.roguelike.updateFieldOfView

internal class PlayerVisionChangeReaction(
    game: TendiwaGame
) : GameReaction<PlayerVision.Change>(game) {

    override fun invoke(stimulus: PlayerVision.Change, done: () -> Unit) {
        val difference = stimulus.old
            .difference(stimulus.new)
        destroyUnseenWalls(difference)
        destroyUnseenThings(difference)
        game.vicinity.updateFieldOfView(stimulus.new)
        showSeenWalls(difference)
        showSeenThings(difference)
        done()
    }

    private fun showSeenWalls(difference: PlayerVision.VisionDifference) {
        val plane = game.reality.space.walls.planeAtZ(game.vicinity.boundsDepth)
        difference.seen
            .filter { plane.hasWallAt(it) }
            .forEach { game.gridActorRegistry.spawnWall(it) }
    }

    private fun destroyUnseenWalls(difference: PlayerVision.VisionDifference) {
        val plane = game.reality.space.walls.planeAtZ(game.vicinity.boundsDepth)
        difference.unseen
            .filter { plane.hasWallAt (it) }
            .forEach { game.gridActorRegistry.removeWallActor(it) }
    }

    private fun showSeenThings(difference: PlayerVision.VisionDifference) {
        game.apply {
            reality.space.realThings
                .planeAtZ(vicinity.boundsDepth)
                .viewOfArea(vicinity.tileBounds)
                .things
                .filter {
                    val tile = it.aspect<Position>().tile
                    difference.seen.any { it == tile }
                }
                .forEach { gridActorRegistry.spawnRealThing(it) }
        }
    }

    private fun destroyUnseenThings(difference: PlayerVision.VisionDifference) {
        game.reality.space.realThings
            .planeAtZ(game.vicinity.boundsDepth)
            .viewOfArea(game.vicinity.tileBounds)
            .things
            .filter { thing ->
                difference.unseen.any { it == thing.aspect<Position>().tile }
            }
            .forEach { game.gridActorRegistry.removeActor(it) }
    }

}
