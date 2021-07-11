package com.github.sh0ckr6.managers

import com.github.sh0ckr6.commands.Command
import com.github.sh0ckr6.commands.IGlobalCommand
import com.github.sh0ckr6.commands.IGuildCommand
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object SlashCommandManager {
    private val commands: MutableList<Command> = mutableListOf()

    fun registerCommand(command: Command) {
        commands.add(command)
    }

    fun registerCommands(vararg commands: Command) {
        this.commands.addAll(commands)
    }

    fun getAllCommands(): List<Command> {
        return commands
    }

    fun getAllGlobalCommands(): List<Command> {
        return commands.filter { it is IGlobalCommand }
    }

    fun getAllGuildCommands(): List<Command> {
        return commands.filter { it is IGuildCommand }
    }

    fun getCommandsFromGuild(guildId: Long): List<Command> {
        return commands.filter { it is IGuildCommand && it.guildId == guildId }
    }

    fun getAllCommandGuildIds(): List<Long> {
        return (commands.filter { it is IGuildCommand } as List<IGuildCommand>).groupBy { it.guildId }.keys.toList()
    }

    fun getAllGlobalCommandData(): List<CommandData> {
        return commands.filter { it is IGlobalCommand }.map { it.commandData }
    }

    fun getAllCommandDataFromGuild(guildId: Long): List<CommandData> {
        return commands.filter { it is IGuildCommand && it.guildId == guildId }.map { it.commandData }
    }
}