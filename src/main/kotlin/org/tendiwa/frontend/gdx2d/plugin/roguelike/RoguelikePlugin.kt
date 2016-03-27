package org.tendiwa.frontend.gdx2d.plugin.roguelike

import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.modules.roguelike.aspects.PlayerVision
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.stimuli.PlacementToSpace
import org.tendiwa.backend.space.stimuli.RemovalFromSpace
import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.TendiwaGdxClientPlugin
import org.tendiwa.frontend.gdx2d.centerOnTile
import org.tendiwa.frontend.gdx2d.gridActors.addActorFactories
import org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions.PlayerVisionChangeReaction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions.RealThingMovementReaction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions.RealThingPlacementToSpaceReaction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.reactions.RealThingRemovalFromSpaceReaction
import org.tendiwa.frontend.gdx2d.plugin.roguelike.setup.setupKeybindings
import org.tendiwa.frontend.gdx2d.plugin.roguelike.setup.setupUi
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
            vicinity.updateFieldOfView(
                playerCharacter
                    .aspect<PlayerVision>()
                    .fieldOfView
            )
            camera.centerOnTile(playerCharacter.aspect<Position>().tile)
            setupKeybindings(game)
            setupUi(game, textureCache)
        }
    }
}

fun RenderingVicinity.updateFieldOfView(fieldOfView: PlayerVision.FieldOfView) {
    this.fieldOfView = fieldOfView.mask
    this.tileBounds = fieldOfView.hull
    this.boundsDepth = fieldOfView.z
}

