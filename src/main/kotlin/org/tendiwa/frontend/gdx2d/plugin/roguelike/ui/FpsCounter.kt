package org.tendiwa.frontend.gdx2d.plugin.roguelike.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import org.tendiwa.frontend.gdx2d.fonts.Fonts
import org.tendiwa.frontend.gdx2d.ui.setBackgroundColor
import java.util.*

class FpsCounter : Table() {
    private var label: Label

    init {
        setBackgroundColor(Color(0.2f, 0.2f, 0.2f, 1.0f))
        label = Label(
            "FPS: N/A",
            Label.LabelStyle().apply {
                font = Fonts.generateFont(
                    Gdx.files.classpath(
                        "org/tendiwa/frontend/gdx/plugins/roguelike/fonts" +
                            "/DejaVuSans.ttf"
                    ),
                    {
                        this.color = Color.WHITE
                        this.borderColor = Color.BLACK
                    }
                )

            }
        )
        add(label)
        Timer().scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    label.setText("FPS: ${Gdx.graphics.framesPerSecond}")
                }

            },
            0,
            400
        )
    }


}
