package me.williamfrisk.bot.eventHandlers.slashCommandHandlers;

import me.williamfrisk.model.database.DBConnection;
import me.williamfrisk.model.riotApi.Summoner;
import me.williamfrisk.utils.Throw;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.function.BiFunction;
import java.util.function.Function;

public class SlashLevelHandler implements CommandHandler {

    private static SlashLevelHandler INSTANCE = null;
    private DBConnection dbConnection;

    public static SlashLevelHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SlashLevelHandler();
        }
        return INSTANCE;
    }

    private SlashLevelHandler() {
        try {
            dbConnection = DBConnection.getInstance();
        } catch (Exception e) {
            Throw.asUnchecked(e);
        }
    }

    @Override
    public SlashCommandData getCommandData() {
        return Commands.slash("level", "The level of the given summoner")
                .addOption(OptionType.STRING, "summoner", "The summoner to look up");
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        //TODO: More fun message
        final BiFunction<String, Long, String> baseMessage = (summonerName, level) -> summonerName + " is level " + level;

        if (event.getOptions().isEmpty()) {
            String summonerName = dbConnection.getSummoner(event.getMember().getEffectiveName()).getString("summonerName");
            Summoner summoner = Summoner.ofSummonerName(summonerName);

            event.reply(baseMessage.apply(summoner.getName(), summoner.getSummonerLevel())).queue();
        } else {
            Summoner summoner = Summoner.ofSummonerName(event.getOption("summoner", OptionMapping::getAsString));
            event.reply(baseMessage.apply(summoner.getName(), summoner.getSummonerLevel())).queue();
        }
    }
}
