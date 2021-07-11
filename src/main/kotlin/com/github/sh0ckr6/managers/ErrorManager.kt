package com.github.sh0ckr6.managers

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.io.File

object ErrorManager {
    fun Throwable.logToDiscord(channel: MessageChannel) {
        val embed: MessageEmbed = EmbedBuilder()
            .setTitle("An Error has Occurred!")
            .setColor(Color(255, 0, 0))
            .addField("Exception", this::class.qualifiedName, false)
            .addField("Reason", this.message, false)
            .addField("Reporting", "Please wait a little bit and try again. If this exception continues to appear, " +
                    "you can [create an issue on the GitHub repo](https://github.com/sh0ckR6/role-colors/issues/new?assignees=&labels=bug&template=bug_report.md&title=%5BBUG%5D+-+Replace+me+with+the+issue+name). If you might know " +
                    "how to fix this issue yourself, come make a [pull request](https://github.com/sh0ckR6/role-colors/compare)!", false)
            .build()
        val stacktraceFile: File = File.createTempFile("stacktrace", ".txt")
        stacktraceFile.writeText("Stacktrace (copy and paste the following in your GitHub Issue): \n\n" + this.stackTraceToString())
        channel.sendMessageEmbeds(embed).queue()
        channel.sendFile(stacktraceFile).queue {
            stacktraceFile.delete()
        }
    }
}