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
import org.tendiwa.frontend.gdx2d.ui.VerticalFlowGroup
import org.tendiwa.frontend.gdx2d.ui.setBackgroundColor
import java.util.*

class InventoryWidget(
    private val inventory: Inventory,
    private val game: TendiwaGame
) : Table() {

    private val itemsToWidgets: MutableMap<Item, Widget> =
        LinkedHashMap()
    private val flowGroup: VerticalFlowGroup = VerticalFlowGroup()

    init {
        setBackgroundColor(Color(0.2f, 0.2f, 0.2f, 1.0f))
        inventory.items
            .forEach { addItemIcon(it) }
        game.frontendStimulusMedium.registerReaction(
            Inventory.Store::class.java,
            StoreReaction()
        )
        game.frontendStimulusMedium.registerReaction(
            Inventory.Lose::class.java,
            LoseReaction()
        )
        add(flowGroup).expandX().expandY().fillX().fillY()
    }

    private fun addItemIcon(item: Item) {
        val widget = ItemWidget(item)
        flowGroup.addActor(widget)
        itemsToWidgets[item] = widget
    }

    inner class StoreReaction : GameReaction<Inventory.Store>(game) {
        override fun invoke(stimulus: Inventory.Store, done: () -> Unit) {
            addItemIcon(stimulus.item)
            done()
        }
    }

    inner class LoseReaction : GameReaction<Inventory.Lose>(game) {
        override fun invoke(stimulus: Inventory.Lose, done: () -> Unit) {
            removeItemIcon(stimulus.item)
            done()
        }
    }

    private fun removeItemIcon(item: Item) {
        flowGroup.removeActor(itemsToWidgets[item])
        itemsToWidgets.remove(item)
    }

    private fun ItemWidget(it: Item): Widget =
        Image(game.textureCache.texture("items/${it.name.string}", 0))
}

