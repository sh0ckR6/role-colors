package com.github.sh0ckr6.commands

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent

/**
 * Interface for [Command]s that have [net.dv8tion.jda.api.interactions.components.Button] components
 *
 * @author sh0ckR6
 * @since 1.0
 */
sealed interface IHasButton {
    /**
     * Function to be run when a button attached
     * to this command is interacted with
     *
     * @param [event] The [ButtonClickEvent] that raised this callback
     *
     * @author sh0ckR6
     * @since 1.0
     */
    fun onButtonInteract(event: ButtonClickEvent)
}
