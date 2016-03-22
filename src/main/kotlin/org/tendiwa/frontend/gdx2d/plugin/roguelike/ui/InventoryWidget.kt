package org.tendiwa.frontend.gdx2d.plugin.roguelike.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.aspects.Inventory
import org.tendiwa.backend.space.aspects.name
import org.tendiwa.frontend.gdx2d.resources.images.NamedTextureCache
import org.tendiwa.frontend.gdx2d.ui.setBackgroundColor

class InventoryWidget(
    private val inventory: Inventory,
    private val textureCache: NamedTextureCache
) : Table() {
    init {
        setBackgroundColor(Color(0.2f, 0.2f, 0.2f, 1.0f))
        setSize(400f, 300f)
        inventory.items.forEach {
            add(ItemWidget(it))
                .minWidth(32f)
                .minHeight(32f)
        }
    }

    private fun ItemWidget(it: Item): Widget =
        Image(textureCache.texture("items/${it.name.string}", 0))
}
