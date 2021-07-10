package com.github.sh0ckr6.commands

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData

/**
 * Base class for all Commands
 * @author sh0ckR6
 * @since 1.0
 */
abstract class Command(
    /**
     * The name of the command
     * @since 1.0
     */
    val name: String,
    /**
     * The description of the command
     * @since 1.0
     */
    val description: String,
    /**
     * The [JDA] client
     *
     * @since 1.0
     */
    val bot: JDA
) : ListenerAdapter() {
    abstract val commandData: CommandData

    /**
     * Override the [net.dv8tion.jda.api.hooks.ListenerAdapter#onSlashCommand]
     *
     * @param[event] The event passed to the function
     * @since 1.0
     * @author sh0ckR6
     */
    override fun onSlashCommand(event: SlashCommandEvent) {
        if (event.name != this.name) return

        run(event.options, event)
    }

    /**
     * Infix function to add options to [CommandData] objects
     *
     * @param[data] The [OptionData] to add to the [CommandData]
     * @return Returns the updated [CommandData]
     * @author sh0ckR6
     * @since 1.0
     */
    private infix fun CommandData.withOption(data: OptionData): CommandData {
        return this.addOptions(data)
    }

    /**
     * Runs the command being called
     *
     * @param[args] The arguments provided to the command
     * @param[event] The [SlashCommandEvent] that called the command
     *
     * @author sh0ckR6
     * @since 1.0
     */
    abstract fun run(args: List<OptionMapping>, event: SlashCommandEvent)

    override fun onSelectionMenu(event: SelectionMenuEvent) {
        when (this) {
            is IHasSelectMenu -> onSelectMenuInteract(event)
        }
    }

    override fun onButtonClick(event: ButtonClickEvent) {
        when (this) {
            is IHasButton -> onButtonInteract(event)
        }
    }
}