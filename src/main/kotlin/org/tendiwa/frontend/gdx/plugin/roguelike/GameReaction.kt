package org.tendiwa.frontend.gdx.plugin.roguelike

import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.client.gdx.TendiwaGame

abstract class GameReaction<S : Stimulus>(
    protected val game: TendiwaGame
): (S) ->Unit
