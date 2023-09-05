package me.williamfrisk.bot.eventHandlers.slashCommandHandlers;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface CommandHandler {

    CommandData getCommandData();
    void handleCommand(SlashCommandInteractionEvent event);
}
