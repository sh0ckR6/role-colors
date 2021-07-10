package com.github.sh0ckr6.commands

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent

sealed interface IHasButton {
    fun onButtonInteract(event: ButtonClickEvent)
}
