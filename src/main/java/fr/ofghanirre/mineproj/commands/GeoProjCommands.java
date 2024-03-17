package fr.ofghanirre.mineproj.commands;

import fr.ofghanirre.mineproj.GeoProjectivePlugin;
import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import fr.ofghanirre.mineproj.cga.operations.EComputeOperation;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GeoProjCommands implements CommandExecutor {
    private static final String PREFIX = "" + ChatColor.AQUA + ChatColor.BOLD + "CGA" + ChatColor.GOLD + " >>> " + ChatColor.RESET;

    private static void sendMessage(CommandSender target, String message) {
        target.sendMessage(PREFIX + message + ChatColor.RESET);
    }

    private record CommandPacket(CommandConsumer command, String description) {}

    private static Map<String, CommandPacket> COMMANDS = new HashMap<>();

    private static Optional<Integer> parseArg(String[] args, int index, int defaultValue) {
        if (args.length <= index) return Optional.of(defaultValue);
        try {
            int targetAmount = Integer.parseInt(args[index]);
            return Optional.of(targetAmount);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    static {
        COMMANDS.put("add", new CommandPacket((sender, args) -> {
            Player player = ((Player) sender);
            int pointsAmount = GeoProjectivePlugin.getInstance().getPointHolder().addPoint(new CGAPoint((player.getLocation())));
            sendMessage(sender, ChatColor.GREEN + "New Point added to memory! " + ChatColor.GRAY + "New size : " + pointsAmount);
        }, "Add a new Point from where the sender stands"));

        COMMANDS.put("remove", new CommandPacket((sender, args) -> {
            parseArg(args, 1, 1).ifPresentOrElse(targetAmount -> {
                int pointsAmount = GeoProjectivePlugin.getInstance().getPointHolder().removePoint(targetAmount);
                if (pointsAmount == -1) {
                    sendMessage(sender, ChatColor.RED + "The memory is empty...");
                } else {
                    if (targetAmount == 1) {
                        sendMessage(sender, ChatColor.GREEN + "Removed last point from memory! " + ChatColor.GRAY + "New size : " + pointsAmount);
                    } else {
                        sendMessage(sender, ChatColor.GREEN + "Removed last " + targetAmount + " points from memory! " + ChatColor.GRAY + "New size : " + pointsAmount);
                    }
                }
            }, () -> sendMessage(sender, ChatColor.RED + "A number was expected as argument"));
        }, "Removes the n last Point recorded in memory, default n is 1"));

        COMMANDS.put("clear", new CommandPacket((sender, args) -> {
            int pointsAmount = GeoProjectivePlugin.getInstance().getPointHolder().clearPoints();
            sendMessage(sender, ChatColor.GREEN + "" + pointsAmount + " points were cleared from the memory !");
        }, "Clear all points from memory"));

        COMMANDS.put("restore", new CommandPacket((sender, args) -> {
            parseArg(args, 1, 1).ifPresentOrElse(targetAmount -> {
                int pointsAmount = GeoProjectivePlugin.getInstance().getPointHolder().restorePoint(targetAmount);
                if (pointsAmount == -1) {
                    sendMessage(sender, ChatColor.RED + "The cache is empty...");
                } else {
                    if (targetAmount == 1) {
                        sendMessage(sender, ChatColor.GREEN + "Restored last deleted point! " + ChatColor.GRAY + "New size : " + pointsAmount);
                    } else {
                        sendMessage(sender, ChatColor.GREEN + "Restored last " + targetAmount +" deleted point! " + ChatColor.GRAY + "New size : " + pointsAmount);
                    }
                }
            }, () -> sendMessage(sender, ChatColor.RED + "A number was expected as argument"));
        }, "Restores the n last deleted Point, default n is 1"));

        COMMANDS.put("size", new CommandPacket((sender, args) -> sendMessage(sender, ChatColor.GREEN + "Size : " + GeoProjectivePlugin.getInstance().getPointHolder().size()), "Get the current amount of Points registered."));

        COMMANDS.put("outer", new CommandPacket((sender, args) -> {
            GeoProjectivePlugin.getInstance().getPointHolder().compute(EComputeOperation.OUTERPRODUCT);
            sendMessage(sender, ChatColor.GREEN + "The points have been computed");
        }, "Execute an outer product on the points"));

        COMMANDS.put("help", new CommandPacket((sender, args) -> {
            sendMessage(sender, ChatColor.GOLD + "User Commands");
            COMMANDS.forEach((name, commandPacket) -> {
                sender.sendMessage("- " + ChatColor.AQUA + name + ChatColor.RESET + ChatColor.GOLD + " : " + ChatColor.WHITE + ChatColor.ITALIC + commandPacket.description + ChatColor.RESET);
            });
        }, "Display users commands" ));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String argument, String[] args) {
        if(!(sender instanceof Player player)) {
            sendMessage(sender, "Only players can run this command !");
            return false;
        }

        if (args.length == 0) {
            COMMANDS.get("help").command.run(player, args);
        } else {
            COMMANDS.getOrDefault(args[0], COMMANDS.get("help")).command.run(player, args);
        }
        return true;
    }
}
