package me.williamfrisk;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordBot {

    private static final String DISCORD_TOKEN = System.getenv("LEAGUE_BOT_DISCORD_TOKEN");

    public static void main(String[] args) {

        JDA jda = JDABuilder.createDefault(DISCORD_TOKEN)
                .setActivity(Activity.playing("with your mom"))
                .build();

    }
}
