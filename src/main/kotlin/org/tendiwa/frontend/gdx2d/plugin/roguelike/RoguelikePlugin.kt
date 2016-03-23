package org.tendiwa.frontend.gdx2d.plugin.roguelike

import com.badlogic.gdx.Input
import org.tendiwa.backend.modules.roguelike.aspects.PlayerVision
import org.tendiwa.backend.modules.roguelike.aspects.playerVision
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.backend.space.stimuli.PlacementToSpace
import org.tendiwa.backend.space.stimuli.RemovalFromSpace
import org.tendiwa.frontend.gdx2d.GameReaction
import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.TendiwaGdxClientPlugin
import org.tendiwa.frontend.gdx2d.centerOnTile
import org.tendiwa.frontend.gdx2d.gridActors.addActorFactories
import org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.DropFirstItemInInventoryAction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.MovePlayerCharacterAction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.actions.PickUpAction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions.PlayerVisionChangeReaction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions.RealThingMovementReaction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions.RealThingRemovalFromSpaceReaction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.ui.setupUi
import org.tendiwa.frontend.generic.RenderingVicinity

class RoguelikePlugin : TendiwaGdxClientPlugin {
    override fun init(game: TendiwaGame) {
        game.apply {
            val playerCharacter = game.playerVolition.host
            gridActorRegistry.addActorFactories(
                CharacterActorFactory(textureCache),
                ItemActorFactory(textureCache)
            )
            frontendStimulusMedium.apply {
                registerReaction(
                    Position.Change::class.java,
                    RealThingMovementReaction(game)
                )
                registerReaction(
                    PlayerVision.Change::class.java,
                    PlayerVisionChangeReaction(game)
                )
                registerReaction(
                    RemovalFromSpace::class.java,
                    RealThingRemovalFromSpaceReaction(game)
                )
                registerReaction(
                    PlacementToSpace::class.java,
                    RealThingPlacementToSpaceReaction(game)
                )
            }
            vicinity.updateFieldOfView(playerCharacter.playerVision.fieldOfView)
            camera.centerOnTile(playerCharacter.position.tile)
            keysSetup.apply {
                MovePlayerCharacterAction(game, playerCharacter)
                    .let {
                        move ->
                        addAction(Input.Keys.LEFT, { move(-1, 0) })
                        addAction(Input.Keys.RIGHT, { move(1, 0) })
                        addAction(Input.Keys.UP, { move(0, 1) })
                        addAction(Input.Keys.DOWN, { move(0, -1) })
                    }
                PickUpAction(game, playerCharacter)
                    .let { pickUp ->
                        addAction(Input.Keys.G, { pickUp(0, 0) })
                    }
                DropFirstItemInInventoryAction(game)
                    .let { drop ->
                        addAction(Input.Keys.D, { drop() })
                    }
            }
            setupUi(game, textureCache)
        }
    }

}

class RealThingPlacementToSpaceReaction(
    game: TendiwaGame
) : GameReaction<PlacementToSpace>(game) {
    override fun invoke(stimulus: PlacementToSpace, done: () -> Unit) {
        game.gridActorRegistry.spawnRealThing(stimulus.thing)
        done()
    }
}

fun RenderingVicinity.updateFieldOfView(fieldOfView: PlayerVision.FieldOfView) {
    this.fieldOfView = fieldOfView.mask
    this.tileBounds = fieldOfView.hull
}

