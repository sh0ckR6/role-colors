package com.github.sh0ckr6

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import javax.security.auth.login.LoginException

/**
 * Main class for the program
 *
 * @since 1.0
 * @author sh0ckR6
 */
object RoleColors {
    /**
     * The [JDA] representing the bot
     */
    private lateinit var bot: JDA

    /**
     * Main entry point for the program
     *
     * @param args The arguments provided by the executor
     * @throws LoginException If the provided token is invalid
     * @throws InterruptedException If this thread is interrupted while waiting
     *
     * @author sh0ckR6
     * @since 1.0
     */
    @Throws(LoginException::class, InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val builder = JDABuilder.createDefault(System.getenv("ROLECOLORSKEY"))
        bot = builder.build()
        bot.awaitReady()
    }
}