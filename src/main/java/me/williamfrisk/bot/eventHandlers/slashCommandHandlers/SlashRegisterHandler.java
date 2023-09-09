package me.williamfrisk.bot.eventHandlers.slashCommandHandlers;

import me.williamfrisk.model.database.DBConnection;
import me.williamfrisk.model.riotApi.Summoner;
import me.williamfrisk.utils.Throw;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.sql.SQLException;

public class SlashRegisterHandler implements CommandHandler {
    private static SlashRegisterHandler INSTANCE = null;
    private DBConnection dbConnection;

    public static SlashRegisterHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SlashRegisterHandler();
        }
        return INSTANCE;
    }

    private SlashRegisterHandler() {
        try {
            dbConnection = DBConnection.getInstance();
        } catch (Exception e) {
            Throw.asUnchecked(e);
        }
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("register", "Register the given summoner name to your discord account")
                .addOption(OptionType.STRING, "summoner", "The summoner to be registered", true);
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        Summoner summoner = Summoner.ofSummonerName(event.getOption("summoner", OptionMapping::getAsString));
        try {
            dbConnection.registerSummoner(summoner.getPuuid(), summoner.getName(), event.getMember().getEffectiveName());
            event.reply("Registered " + summoner.getName() + " to " + event.getMember().getEffectiveName() + " successfully").queue();
        } catch (SQLException e) {
            event.reply("Couldn't register " + summoner.getName() + " to " + event.getMember().getEffectiveName()).queue();
            Throw.asUnchecked(e);
        }

    }
}
