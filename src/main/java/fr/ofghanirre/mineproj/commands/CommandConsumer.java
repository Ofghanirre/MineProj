package fr.ofghanirre.mineproj.commands;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CommandConsumer {
    void run(CommandSender sender, String[] args);
}
