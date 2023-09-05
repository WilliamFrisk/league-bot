package me.williamfrisk.bot.eventHandlers.slashCommandHandlers;

import me.williamfrisk.model.riotApi.Summoner;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class SlashLevelHandler implements CommandHandler {

    private static SlashLevelHandler INSTANCE = null;

    public static SlashLevelHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SlashLevelHandler();
        }
        return INSTANCE;
    }

    private SlashLevelHandler() {}

    @Override
    public SlashCommandData getCommandData() {
        return Commands.slash("level", "The level of the given summoner")
                .addOption(OptionType.STRING, "summoner", "The summoner to look up", true);
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        //TODO: More fun message
        event.reply(Summoner.ofSummonerName(event.getOption("summoner", OptionMapping::getAsString)).getSummonerLevel() + "").queue();
    }
}
