package com.github.sh0ckr6.commands

import com.github.sh0ckr6.managers.ErrorManager.logToDiscord
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.requests.RestAction

/**
 * Base class for all Commands
 *
 * @property [name] The name of the command
 * @property [description] The description of the command
 * @property [bot] The [JDA] client
 *
 * @author sh0ckR6
 * @since 1.0
 */
abstract class Command(val name: String, val description: String, val bot: JDA) : ListenerAdapter() {

    abstract val commandData: CommandData

    /**
     * Extension method for [RestAction] to automatically set [replyMessage] after queueing an interaction reply
     *
     * @author sh0ckR6
     * @since 1.0
     * @see replyMessage
     */
    fun RestAction<InteractionHook>.queueAndSetMessage(command: Command) {
        this.queueAndSetMessage(command, null, null)
    }

    /**
     * Extension method for [RestAction] to automatically set [replyMessage] after queueing an interaction reply
     * that allows for custom success handling
     *
     * @author sh0ckR6
     * @since 1.0
     * @see replyMessage
     */
    fun RestAction<InteractionHook>.queueAndSetMessage(command: Command, success: (InteractionHook) -> Unit) {
        this.queueAndSetMessage(command, success, null)
    }

    /**
     * Extension method for [RestAction] to automatically set [replyMessage] after queueing an interaction reply
     * that allows for custom success and error handling
     *
     * @author sh0ckR6
     * @since 1.0
     * @see replyMessage
     */
    fun RestAction<InteractionHook>.queueAndSetMessage(command: Command, success: ((InteractionHook) -> Unit)?, failure: ((Throwable) -> Unit)?) {
        this.queue({
            it.retrieveOriginal().queue { message ->
                command.replyMessage = message
                success?.invoke(it)
            }
        }, failure)
    }

    /**
     * The [Message] this command set in response to the execution of the command
     *
     * **IMPORTANT**: This value **must** be set after sending a reply for component interactions to work properly
     * if you are not queueing with the extension method [queueAndSetMessage]
     *
     * Example (using [queue][RestAction.queue]):
     * ```kotlin
     * override fun run(args: List<OptionMapping>, event: SlashCommandEvent) {
     *      event.reply("Hello, World!").queue {
     *           it.retrieveOriginal().queue { message ->
     *               replyMessage = message
     *           }
     *           // Other reply handling code...
     *      }
     * }
     * ```
     *
     * Example (using [queueAndSetMessage][queueAndSetMessage]):
     * ```kotlin
     * override fun run(args: List<OptionMapping>, event: SlashCommandEvent {
     *      event.reply("Hello, World!").queueAndSetMessage {
     *           // Reply handling code
     *      }
     * }
     * ```
     *
     * @since 1.0
     * @see queueAndSetMessage
     */
    var replyMessage: Message? = null

    /**
     * Override the [onSlashCommand][net.dv8tion.jda.api.hooks.ListenerAdapter#onSlashCommand]
     * callback provided by [ListenerAdapter]
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

    /**
     * Function to be run when a select menu attached
     * to this command is interacted with
     *
     * @param [event] The [SelectionMenuEvent] that raised this callback
     *
     * @author sh0ckR6
     * @since 1.0
     * @throws MissingInteractionMessageException If [replyMessage] is not set (see [queueAndSetMessage])
     */
    @Throws(MissingInteractionMessageException::class)
    override fun onSelectionMenu(event: SelectionMenuEvent) {
        when (this) {
            is IHasSelectMenu -> {
                try {
                    if (replyMessage == null) throw MissingInteractionMessageException()
                } catch (e: Exception) {
                    e.logToDiscord(event.channel)
                    return
                }
                if (event.interaction.message!!.id == replyMessage!!.id) {
                    onSelectMenuInteract(event)
                }
            }
        }
    }

    /**
     * Function to be run when a button attached
     * to this command is interacted with
     *
     * @param [event] The [ButtonClickEvent] that raised this callback
     *
     * @author sh0ckR6
     * @since 1.0
     * @throws MissingInteractionMessageException If [replyMessage] is not set (see [queueAndSetMessage])
     */
    @Throws(MissingInteractionMessageException::class)
    override fun onButtonClick(event: ButtonClickEvent) {
        when (this) {
            is IHasButton -> {
                try {
                    if (replyMessage == null) throw MissingInteractionMessageException()
                } catch (e: Exception) {
                    e.logToDiscord(event.channel)
                    return
                }
                if (event.interaction.message!!.id == replyMessage!!.id) {
                    onButtonInteract(event)
                }
            }
        }
    }
}