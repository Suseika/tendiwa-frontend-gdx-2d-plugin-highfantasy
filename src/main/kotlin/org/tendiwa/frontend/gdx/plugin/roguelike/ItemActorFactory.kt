package org.tendiwa.frontend.gdx.plugin.roguelike

import com.badlogic.gdx.scenes.scene2d.Actor
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.space.aspects.name
import org.tendiwa.client.gdx.gridActors.ActorFactory
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.client.gdx.walls.RegionActor

class ItemActorFactory(
    private val textureCache: NamedTextureCache
) : ActorFactory {

    override fun creates(realThing: RealThing): Boolean =
        realThing is Item

    override fun create(realThing: RealThing): Actor =
        RegionActor(
            textureCache.texture(
                "items/${realThing.name.string}",
                0
            )
        )

}
