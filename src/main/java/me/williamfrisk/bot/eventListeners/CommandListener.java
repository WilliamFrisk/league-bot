package me.williamfrisk.bot.eventListeners;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Collection;

public interface CommandListener {
    Collection<CommandData> getAllCommands();
}
