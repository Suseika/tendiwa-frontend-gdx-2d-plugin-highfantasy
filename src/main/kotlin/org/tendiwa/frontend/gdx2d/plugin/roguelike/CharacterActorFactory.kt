package org.tendiwa.frontend.gdx2d.plugin.roguelike

import com.badlogic.gdx.scenes.scene2d.Actor
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.modules.roguelike.archetypes.Character
import org.tendiwa.backend.space.aspects.name
import org.tendiwa.frontend.gdx2d.gridActors.ActorFactory
import org.tendiwa.frontend.gdx2d.resources.images.NamedTextureCache
import org.tendiwa.frontend.gdx2d.walls.RegionActor

class CharacterActorFactory(
    private val cache: NamedTextureCache
) : ActorFactory {
    override fun creates(realThing: RealThing): Boolean =
        realThing is Character

    override fun create(realThing: RealThing): Actor =
        RegionActor(
            cache.texture("characters/" + realThing.name.string, 0)
        )
}
