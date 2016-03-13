package org.tendiwa.frontend.gdx.plugin.roguelike

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import org.tendiwa.backend.modules.roguelike.aspects.PlayerVision
import org.tendiwa.backend.modules.roguelike.aspects.playerVision
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.backend.space.chunks.chunkWithTile
import org.tendiwa.backend.space.realThing.realThings
import org.tendiwa.backend.space.realThing.viewOfArea
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.backend.space.walls.walls
import org.tendiwa.client.gdx.TendiwaGame
import org.tendiwa.client.gdx.TendiwaGdxClientPlugin
import org.tendiwa.frontend.generic.move
import org.tendiwa.plane.grid.masks.contains
import org.tendiwa.plane.grid.tiles.Tile

class RoguelikePlugin : TendiwaGdxClientPlugin {
    override fun init(game: TendiwaGame) {
        fun TendiwaGame.centerCameraOnTile(targetTile: Tile) {
            camera.position.set(
                targetTile.x.toFloat() + 0.5f,
                targetTile.y.toFloat() + 0.5f,
                0f
            )
        }

        game.apply {
            val playerCharacter = reality.hostOf(playerVolition)
            fun movePlayerCharacter(
                dx: Int,
                dy: Int,
                // TODO: Just use the variable from closure when this bug is
                // TODO: fixed: https://youtrack.jetbrains.com/issue/KT-8689
                game: TendiwaGame
            ): Boolean {
                if (dx != 0 || dy != 0) {
                    val currentTile = playerCharacter.position.tile
                    val targetTile = Tile(
                        currentTile.x + dx,
                        currentTile.y + dy
                    )
                    if (!game.reality.space.hull.contains(targetTile)) {
                        return false
                    }
                    centerCameraOnTile(targetTile)
                    playerVolition.move(
                        reality,
                        targetTile.x,
                        targetTile.y
                    )
                    return true
                }
                return false
            }
            frontendStimulusMedium.apply {
                registerReaction(
                    Position.Change::class.java,
                    { stimulus ->
                        gridActorRegistry
                            .actorOf(stimulus.host)
                            .addAction(
                                MoveToAction().apply {
                                    x = stimulus.to.x.toFloat()
                                    y = stimulus.to.y.toFloat()
                                    duration = 0.1f
                                }
                            )
                    }
                )
                registerReaction(
                    PlayerVision.Change::class.java,
                    { stimulus ->
                        vicinity.tileBounds = stimulus.new.hull
                        vicinity.fieldOfView = stimulus.new.mask
                        stimulus.old
                            .difference(stimulus.new)
                            .let { difference ->
                                difference.seen.forEach {
                                    if (reality.space.walls.chunkWithTile(it).wallAt(it) != WallType.void) {
                                        gridActorRegistry.spawnWall(it)
                                    }
                                }
                                reality.space.realThings
                                    .viewOfArea(vicinity.tileBounds)
                                    .things
                                    .filter {
                                        val tile = it.position.tile
                                        difference.seen.any { it == tile }
                                    }
                                    .forEach {
                                        gridActorRegistry.spawnRealThing(it)
                                    }
                                difference.unseen.forEach {
                                    if (reality.space.walls.chunkWithTile(it).wallAt(it) != WallType.void) {
                                        gridActorRegistry.removeWallActor(it)
                                    }
                                }
                                reality.space.realThings
                                    .viewOfArea(vicinity.tileBounds)
                                    .things
                                    .filter {
                                        val tile = it.position.tile
                                        difference.unseen.any { it == tile }
                                    }
                                    .forEach {
                                        gridActorRegistry.removeActor(it.position.tile, it)
                                    }
                            }
                    }
                )
            }
            camera.position.set(
                playerCharacter.position.tile.x.toFloat(),
                playerCharacter.position.tile.y.toFloat(),
                0f
            )
            vicinity.fieldOfView = playerCharacter.playerVision.fieldOfView.mask
            vicinity.tileBounds = playerCharacter.playerVision.fieldOfView.hull
            keysSetup.apply {
                addAction(
                    Input.Keys.LEFT,
                    { movePlayerCharacter (-1, 0, game) }
                )
                addAction(
                    Input.Keys.RIGHT,
                    { movePlayerCharacter(1, 0, game) }
                )
                addAction(
                    Input.Keys.UP,
                    { movePlayerCharacter(0, 1, game) }
                )
                addAction(
                    Input.Keys.DOWN,
                    { movePlayerCharacter(0, -1, game) }
                )
            }
        }
    }
}

