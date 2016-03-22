package org.tendiwa.frontend.gdx2d.plugin.roguelike.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.aspects.Inventory
import org.tendiwa.backend.space.aspects.name
import org.tendiwa.frontend.gdx2d.GameReaction
import org.tendiwa.frontend.gdx2d.TendiwaGame
import org.tendiwa.frontend.gdx2d.ui.setBackgroundColor

class InventoryWidget(
    private val inventory: Inventory,
    private val game: TendiwaGame
) : Table() {
    init {
        setBackgroundColor(Color(0.2f, 0.2f, 0.2f, 1.0f))
        setSize(400f, 300f)
        inventory.items
            .forEach { addItemIcon(it) }
        game.frontendStimulusMedium.registerReaction(
            Inventory.Store::class.java,
            StoreReaction()
        )
    }

    private fun addItemIcon(it: Item) {
        add(ItemWidget(it))
            .minWidth(32f)
            .minHeight(32f)
    }

    inner class StoreReaction : GameReaction<Inventory.Store>(game) {
        override fun invoke(stimulus: Inventory.Store, done: () -> Unit) {
            addItemIcon(stimulus.item)
            done()
        }

    }

    private fun ItemWidget(it: Item): Widget =
        Image(game.textureCache.texture("items/${it.name.string}", 0))
}

