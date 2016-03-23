package org.tendiwa.frontend.gdx2d.plugin.roguelike

import com.badlogic.gdx.scenes.scene2d.Actor
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.space.aspects.Name
import org.tendiwa.frontend.gdx2d.gridActors.ActorFactory
import org.tendiwa.frontend.gdx2d.resources.images.NamedTextureCache
import org.tendiwa.frontend.gdx2d.walls.RegionActor

class ItemActorFactory(
    private val textureCache: NamedTextureCache
) : ActorFactory {

    override fun creates(realThing: RealThing): Boolean =
        realThing is Item

    override fun create(realThing: RealThing): Actor =
        RegionActor(
            textureCache.texture(
                "items/${realThing.aspect<Name>().string}",
                0
            )
        )

}
