package com.github.sh0ckr6.commands

import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent

/**
 * Interface for [Command]s that have [net.dv8tion.jda.api.interactions.components.selections.SelectionMenu] components
 *
 * @author sh0ckR6
 * @since 1.0
 */
sealed interface IHasSelectMenu {
    /**
     * Function to be run when a select menu attached
     * to this command is interacted with
     *
     * @param [event] The [SelectionMenuEvent] that raised this callback
     *
     * @author sh0ckR6
     * @since 1.0
     */
    fun onSelectMenuInteract(event: SelectionMenuEvent)
}
