package me.williamfrisk.bot.eventListeners;

import me.williamfrisk.bot.eventHandlers.slashCommandHandlers.CommandHandler;
import me.williamfrisk.bot.eventHandlers.slashCommandHandlers.SlashLevelHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class SlashCommandListener extends ListenerAdapter implements CommandListener {

    private static final List<CommandHandler> commandHandlers = List.of(
            SlashLevelHandler.getInstance()
    );

    public static SlashCommandListener create() {
        return new SlashCommandListener();
    }

    private SlashCommandListener() {}

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (CommandHandler handler : commandHandlers) {
            if (handler.getCommandData().getName().equals(event.getName())) {
                handler.handleCommand(event);
                break;
            }
        }
    }

    @Override
    public Collection<CommandData> getAllCommands() {
        return commandHandlers.stream().map(CommandHandler::getCommandData).toList();
    }
}
