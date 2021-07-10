package com.github.sh0ckr6.commands

import com.github.sh0ckr6.Settings
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

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
     * The usage of the command
     * @since 1.0
     */
    val usage: String,
    /**
     * The command's aliases
     * @since 1.0
     */
    val aliases: List<String>
) : ListenerAdapter() {

    /**
     * Override the [net.dv8tion.jda.api.hooks.ListenerAdapter#onGuildMessageReceived]
     *
     * @param[event] The event passed to the function
     * @since 1.0
     * @author sh0ckR6
     */
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        // Store message object in variable
        val message = event.message

        // Ensure command was not called by a bot
        if (event.author.isBot) return

        // Ensure command was actually called and not just a random message
        if (!message.contentStripped.startsWith(Settings.prefix + name)) return

        // Parse arguments
        val argsRaw: List<String> = message.contentStripped.split(" ")
        val args: List<String> = if (argsRaw.size == 1) emptyList() else argsRaw.subList(1, argsRaw.size - 1)

        // Run the command
        run(args, event);
    }

    /**
     * Runs the command being called
     *
     * @param[args] The arguments provided to the command
     * @param[event] The GuildMessageEvent that called the command
     *
     * @author sh0ckR6
     * @since 1.0
     */
    abstract fun run(args: List<String>, event: GuildMessageReceivedEvent)
}