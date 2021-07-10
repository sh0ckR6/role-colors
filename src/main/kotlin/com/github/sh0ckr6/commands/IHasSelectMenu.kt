package com.github.sh0ckr6.commands

import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent

sealed interface IHasSelectMenu {
    fun onSelectMenuInteract(event: SelectionMenuEvent)
}
