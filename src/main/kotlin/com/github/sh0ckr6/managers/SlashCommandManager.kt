package com.github.sh0ckr6.managers

import com.github.sh0ckr6.commands.Command
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

    fun getAllCommandData(): List<CommandData> {
        return commands.map { it.commandData }
    }
}