package me.williamfrisk;

import me.williamfrisk.bot.eventListeners.CommandListener;
import me.williamfrisk.bot.eventListeners.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class DiscordBot {
    // TODO: UTs
    // TODO: Setup database
        // Remember users summoner name
        // Be able to get summoner details from member name
        // Be able to get a leaderboard
        // Be able to put new entries
        // Be able to update existing entries
        // Be able to remove old entries
    // TODO: Further API calls
    // TODO: Update DB from API once every 30 min or on request
    // TODO: Setup CI/CD with automatic UT runs -- VERY OPTIONAL
    // TODO: Host somewhere



    private static final String DISCORD_TOKEN = System.getenv("LEAGUE_BOT_DISCORD_TOKEN");

    private static final List<CommandListener> commandListeners = List.of(
            SlashCommandListener.create()
    );

    public static void main(String[] args) throws InterruptedException {

        JDA jda = JDABuilder.createDefault(DISCORD_TOKEN)
                .setActivity(Activity.playing("with your mom"))
                .addEventListeners(commandListeners.toArray())
                .build();

        jda.awaitReady();
        jda.updateCommands().addCommands(
                commandListeners.stream()
                        .flatMap(commandListener -> commandListener.getAllCommands().stream())
                        .toList()
        ).queue();
    }
}
